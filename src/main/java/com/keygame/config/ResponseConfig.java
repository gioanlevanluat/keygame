package com.keygame.config;

import com.keygame.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseConfig {

    public static ResponseEntity<?> error(String message) {
        return new ResponseEntity(BaseResponse.builder().isSuccess(false).message(message).build(), HttpStatus.OK);
    }

    public static ResponseEntity<?> error(String errorCode, String message) {
        return new ResponseEntity(BaseResponse.builder().isSuccess(false).errorCode(errorCode).message(message).build(), HttpStatus.OK);
    }

    public static ResponseEntity<?> success(Object content) {
        return new ResponseEntity(BaseResponse.builder().isSuccess(true).content(content).build(), HttpStatus.OK);
    }
}
