package com.ufla.services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import com.ufla.models.Payload;
import com.ufla.models.Status;
import com.ufla.models.dto.PayloadDTO;
import io.quarkiverse.rabbitmqclient.RabbitMQClient;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class MessageService {
    private static final Logger log = LoggerFactory.getLogger(MessageService.class);
    @Inject
    RabbitMQClient rabbitMQClient;

    @ConfigProperty(name="rabbimq.exchange")
    String exchange;

    @ConfigProperty(name="rabbitmq.queue.send")
    String queue;

    @ConfigProperty(name="rabbitmq.queue.consumer")
    String queueReady;

    private Channel channel;
    public void onApplicationStart(@Observes StartupEvent event) {
        // on application start prepare the queus and message listener
        setupQueues();
    }

    private void setupQueues() {
        try {
            // create a connection
            Connection connection = rabbitMQClient.connect();
            // create a channel
            channel = connection.createChannel();
            // declare exchanges and queues
            channel.exchangeDeclare(exchange, BuiltinExchangeType.TOPIC, true);
            channel.queueDeclare(queue, true, false, false, null);
            channel.queueBind(queue, exchange, "#");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void send(PayloadDTO payloadDTO) {
        try {
            var jsonWritter = new ObjectMapper().writer().withDefaultPrettyPrinter();
            var json = jsonWritter.writeValueAsString(payloadDTO);
            // send a message to the exchange
            channel.basicPublish(exchange, "#", null, json.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

