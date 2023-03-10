package com.ufla.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class StorageExceptionMapper implements ExceptionMapper<StorageException> {

    @Override
    public Response toResponse(StorageException e){
        return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage()).build();
    }
}
