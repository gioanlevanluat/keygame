package com.keygame.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CartDto {
    private List<CartItemDto> cartItems;
    private Long paymentMethod;
}
