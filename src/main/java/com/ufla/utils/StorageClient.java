package com.ufla.utils;

import io.minio.MinioClient;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Singleton;

@Singleton
public class StorageClient {

    @ConfigProperty(name="minio.endpoint", defaultValue="http://localhost:9000")
    public static String endpoint;

    @ConfigProperty(name="minio.secretKey")
    public static String secretKey;

    @ConfigProperty(name="minio.accessKey", defaultValue = "ROOTNAME")
    public static String acessKey;

    public static MinioClient getMinioClient(){
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(acessKey, secretKey)
                .build();
    }
}
