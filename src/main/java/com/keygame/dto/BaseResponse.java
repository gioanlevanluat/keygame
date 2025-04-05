package com.keygame.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class BaseResponse {
    private String errorCode;
    private Object content;
    private String message;
    private Boolean isSuccess;
}
