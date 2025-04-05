package com.keygame.config.exceptionhandler;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String errorCode;
    private String message;
    private HttpStatus httpStatus;

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
