package com.keygame.config.exceptionhandler;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errorCode;
    private String message;
    private HttpStatus httpStatus;

    public AuthException(String message) {
        this.message = message;
    }

    public AuthException(String errorCode, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
