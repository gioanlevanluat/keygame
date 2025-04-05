package com.keygame.config.exceptionhandler;

import java.io.Serializable;

public class StorageException extends RuntimeException implements Serializable {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public StorageException(Throwable cause) {
        super(cause);
    }
}
