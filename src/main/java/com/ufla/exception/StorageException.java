package com.ufla.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

public class StorageException extends Exception implements Serializable {

    public StorageException(String message) {
    }
}
