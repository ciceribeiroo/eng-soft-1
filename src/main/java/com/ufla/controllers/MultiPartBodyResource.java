package com.ufla.controllers;

import com.ufla.exception.StorageException;
import com.ufla.models.MultiPartBody;
import com.ufla.models.Payload;
import com.ufla.models.Status;
import com.ufla.models.dto.PayloadDTO;
import com.ufla.services.MessageService;
import com.ufla.services.PayloadService;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.annotation.security.RolesAllowed;
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

    @Inject
    MessageService messageService;

    @POST
    @Transactional
    @RolesAllowed({"manager"})
    public void convertMp4ToMp3(@Valid @MultipartForm MultiPartBody data) throws Exception {
        logger.info("Starting converting mp4 to mp3 - " + data);
        var payloadToPersist = Payload.multiPartToPayload(data);

        payloadToPersist.setStatus(Status.converting);
        PayloadDTO payloadDTO = payloadService.sendObjectToStorage(payloadToPersist, data.getFile());
        messageService.send(payloadDTO);
        payloadToPersist.setBucketName(payloadDTO.getBucketName());
        payloadToPersist.persist();
        logger.info("Payload created " + payloadToPersist);
    }

    @GET
    public Response getAll(){
        logger.info("getting all payload");
        return Response.ok(Payload.listAll()).build();
    }

    @Path("{id}")
    @GET
    public Response get(String id){
        return Response.ok(Payload.list("idApp", id)).build();
    }
}
