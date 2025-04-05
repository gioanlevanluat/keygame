package com.keygame.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddTagDto {
    @NotBlank
    private Long productId;
    @NotBlank
    private Long tagId;
}
