package com.ufla.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payload extends PanacheEntity {

    private String idApp;
    private String username;
    private NotificationType notificationType;
    private String phone;
    private String fileName;
    private String email;
    private String bucketName;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date UpdatedAt;
    private Status status;
    private String contentType;

    public  static  Payload multiPartToPayload(MultiPartBody multiPartBody){
        return Payload.builder()
                .idApp(UUID.randomUUID().toString())
                .username(multiPartBody.getUsername())
                .notificationType(multiPartBody.getNotificationType())
                .phone(multiPartBody.getPhone())
                .fileName(multiPartBody.getFileName())
                .email(multiPartBody.getEmail())
                .contentType(multiPartBody.getContentType())
                .build();
    }

}
