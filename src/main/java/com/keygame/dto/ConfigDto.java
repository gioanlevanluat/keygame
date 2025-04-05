package com.keygame.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigDto {
    private Long id;
    @NotBlank
    private String code;
    @NotBlank
    private String value;
}
