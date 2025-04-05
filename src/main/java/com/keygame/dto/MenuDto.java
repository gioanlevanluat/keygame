package com.keygame.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MenuDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String param;
    private List<MenuDto> children;
    @NotBlank
    private String code;
    private Integer index;
    private String backgroundColor;
    private String icon;
}