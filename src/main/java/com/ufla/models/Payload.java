package com.ufla.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Entity;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payload extends PanacheEntity {
    String username;
    NotificationType notificationType;
    public String phone;
    public String fileName;
    public String email;
    public String bucketName;
    @CreationTimestamp
    public Date createdAt;
    @UpdateTimestamp
    public Date UpdatedAt;
    public Status status;

    public String contentType;

    public  static  Payload MuitiPartToPayload(MultiPartBody multiPartBody){
        return Payload.builder()
                .username(multiPartBody.getUsername())
                .notificationType(multiPartBody.getNotificationType())
                .phone(multiPartBody.getPhone())
                .fileName(multiPartBody.getFileName())
                .email(multiPartBody.getEmail())
                .contentType(multiPartBody.getContentType())
                .build();
    }
}
