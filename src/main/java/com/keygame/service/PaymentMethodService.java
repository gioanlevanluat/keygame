package com.keygame.service;

import com.keygame.dto.response.PaymentMethodDto;

import java.util.List;

public interface PaymentMethodService {
    List<PaymentMethodDto> getAll();
}
