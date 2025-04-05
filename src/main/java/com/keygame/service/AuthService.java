package com.keygame.service;

import com.keygame.dto.request.LoginDto;
import com.keygame.dto.RegistryDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    String login(LoginDto loginDto);
    Boolean registry(RegistryDto registryDto, HttpServletRequest httpServletRequest);

    String loginGoogle(String code);

//    String refresh(String phone);
}
