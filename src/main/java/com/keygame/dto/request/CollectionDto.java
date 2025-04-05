package com.keygame.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CollectionDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String code;
    @NotBlank
    private String description;
}
