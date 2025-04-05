package com.keygame.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCartDto {
    private Long productId;
    private Long quantity;
}
