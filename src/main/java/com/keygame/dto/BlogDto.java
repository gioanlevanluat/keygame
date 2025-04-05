package com.keygame.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class BlogDto {
    private Long id;
    @NotBlank
    private String content;
    @NotBlank
    private String title;
    private String collection;
}
