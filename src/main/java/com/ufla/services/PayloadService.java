package com.ufla.services;

import com.ufla.exception.StorageException;
import com.ufla.models.Payload;
import com.ufla.utils.StorageClient;
import io.minio.ObjectWriteResponse;
import io.minio.UploadObjectArgs;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import org.jboss.logging.Logger;


@ApplicationScoped
public class PayloadService {

    @Inject
    StorageClient storageClient;

    @Inject
    Logger logger;

    public void sendObjectToStorage(Payload payload, File file) throws StorageException {
        try {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket("teste")
                    .object(payload.getFileName())
                    .filename(file.getAbsolutePath())
                    .contentType(payload.getContentType())
                    .build();

            ObjectWriteResponse response = StorageClient.getMinioClient().uploadObject(uploadObjectArgs);
            logger.info("Response from Minio"+payload);
        }
        catch (Exception ex){
            throw new StorageException(ex.getMessage());
        }
    }
}
