package com.keygame.service;

import com.keygame.dto.CartDto;
import com.keygame.dto.response.CartInfoDto;

public interface CartService {

    CartInfoDto getCartInfo(CartDto cartDto);
}
