package com.keygame.controller;

import com.keygame.config.ResponseConfig;
import com.keygame.dto.request.LoginDto;
import com.keygame.dto.RegistryDto;
import com.keygame.service.AuthService;
import com.keygame.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto loginDto) {
        return ResponseConfig.success(authService.login(loginDto));
    }

    @PostMapping("/registry")
    public ResponseEntity<?> registry(@RequestBody @Valid RegistryDto registryDto, HttpServletRequest httpServletRequest) {
        return ResponseConfig.success(authService.registry(registryDto, httpServletRequest));
    }

    @GetMapping("/login-google")
    public ResponseEntity<?> loginGoogle(@RequestParam String code) {
        return ResponseConfig.success(authService.loginGoogle(code));
    }

    @GetMapping("/login-facebook")
    public ResponseEntity<?> loginFacebook(@RequestParam String code) {
        return ResponseConfig.success(authService.loginGoogle(code));
    }

}
