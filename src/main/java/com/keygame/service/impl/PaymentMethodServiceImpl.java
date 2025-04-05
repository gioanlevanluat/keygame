package com.keygame.service.impl;

import com.keygame.dto.response.PaymentMethodDto;
import com.keygame.repository.PaymentMethodRepository;
import com.keygame.service.PaymentMethodService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    PaymentMethodRepository paymentMethodRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<PaymentMethodDto> getAll() {
        return paymentMethodRepository.getAll().stream().map(entity -> modelMapper.map(entity, PaymentMethodDto.class)).toList();
    }
}
