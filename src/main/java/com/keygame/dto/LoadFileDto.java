package com.keygame.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class LoadFileDto {
    @NotBlank
    private String path;
}
