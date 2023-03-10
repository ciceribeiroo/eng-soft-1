package com.ufla.controllers;

import com.ufla.exception.StorageException;
import com.ufla.models.MultiPartBody;
import com.ufla.models.Payload;
import com.ufla.models.Status;
import com.ufla.services.PayloadService;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

@Path("/api/v1/convert")
public class MultiPartBodyResource {

    @Inject
    Logger logger;

    @Inject
    PayloadService payloadService;

    @POST
    @Transactional
    public void convertMp4ToMp3(@Valid @MultipartForm MultiPartBody data) throws StorageException {
        logger.info("starting converting mp4 para mp3");
        var payloadToPersist = Payload.MuitiPartToPayload(data);
        payloadToPersist.setStatus(Status.converting);
        //payloadToPersist.persist();
        payloadService.sendObjectToStorage(payloadToPersist, data.getFile());
        payloadToPersist.setStatus(Status.converted);
        logger.info("payload persisted"+payloadToPersist);
    }

    @GET
    public Response getAll(){
        logger.info("getting all payload");
        return Response.ok(Payload.listAll()).build();
    }
}
