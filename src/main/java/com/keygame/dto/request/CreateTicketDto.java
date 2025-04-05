package com.keygame.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateTicketDto {
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private List<String> files;
}
