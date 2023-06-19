package com.ufla.services;

import com.ufla.exception.StorageException;
import com.ufla.models.Payload;
import com.ufla.models.dto.PayloadDTO;
import com.ufla.utils.StorageClient;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.ObjectWriteResponse;
import io.minio.UploadObjectArgs;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.File;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;


@ApplicationScoped
public class PayloadService {

    @Inject
    StorageClient storageClient;

    @Inject
    Logger logger;

    @ConfigProperty(name="bucket.name")
    String bucketName;

    private void makeBucket() throws Exception{
        BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket(bucketName).build();
        if(storageClient.getMinioClient().bucketExists(bucketExistsArgs)){
            logger.info("Bucket "+bucketName+" already exist");
        } else{
            logger.info("Bucket "+bucketName+" does not already exist");
            MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder().bucket(bucketName).build();
            storageClient.getMinioClient().makeBucket(makeBucketArgs);
            logger.info("Bucket "+bucketName+" created");
        }
    }

    @Transactional
    public PayloadDTO sendObjectToStorage(Payload payload, File file) throws Exception {
        ObjectWriteResponse response;
        this.makeBucket();
        try {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket(this.bucketName)
                    .object(payload.getFileName())
                    .filename(file.getAbsolutePath())
                    .contentType(payload.getContentType())
                    .build();

            response = storageClient.getMinioClient().uploadObject(uploadObjectArgs);

        }
        catch (Exception ex){
            throw new StorageException(ex.getMessage());
        }

        return PayloadDTO.builder()
                .bucketName(response.bucket())
                .fileName(payload.fileName)
                .build();
    }
}
