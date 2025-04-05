package com.keygame.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class PlatformDto {
    private Long id;
    @NotBlank
    private String name;
}
