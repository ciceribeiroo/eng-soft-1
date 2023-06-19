package com.ufla.utils;

import io.minio.MinioClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;

@ApplicationScoped
public class StorageClient {

    @ConfigProperty(name="minio.endpoint", defaultValue="http://localhost:9000")
    String endpoint;

    @ConfigProperty(name="minio.secretKey", defaultValue = "ENGSOFTWARE")
    String secretKey;

    @ConfigProperty(name="minio.accessKey")
    String accessKey;

    public MinioClient getMinioClient(){
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}
