package com.keygame.service.impl;

import com.keygame.config.exceptionhandler.BusinessException;
import com.keygame.config.security.GoogleUtils;
import com.keygame.config.security.JwtUtils;
import com.keygame.dto.GooglePojo;
import com.keygame.dto.Mail;
import com.keygame.dto.RegistryDto;
import com.keygame.dto.request.LoginDto;
import com.keygame.entity.ERole;
import com.keygame.entity.Role;
import com.keygame.entity.User;
import com.keygame.repository.RoleRepository;
import com.keygame.repository.UserRepository;
import com.keygame.service.AuthService;
import com.keygame.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.keygame.common.ErrorConstant.*;

@Service
public class AuthServiceImpl implements AuthService {

    Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    MailService mailService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private GoogleUtils googleUtils;

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.generateJwtToken(loginDto.getEmail());
    }

    @Override
    public Boolean registry(RegistryDto registryDto, HttpServletRequest httpServletRequest) {
        User user = userRepository.findByEmail(registryDto.getEmail());
        if (user != null) {
            if (!user.isActive()) {
                user.setPassword(passwordEncoder.encode(registryDto.getPassword()));
                user.setSubscriber(registryDto.isSubscriber());
                userRepository.save(user);
            } else {
                throw new BusinessException(EMAIL_EXIST);
            }
        }
        User newUser = new User();
        newUser.setEmail(registryDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(registryDto.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new BusinessException(ROLE_NOT_FOUND));
        roles.add(userRole);
        newUser.setRoles(roles);
        newUser.setSubscriber(registryDto.isSubscriber());
        newUser.setActive(false);
        userRepository.save(newUser);
        Mail mail = new Mail();
        mail.setSubject("");
        try {
            mailService.sendEmail(mail);
        } catch (MessagingException e) {
            logger.error("Send mail verify error", e);
        }
        return true;
    }

    @Override
    public String loginGoogle(String code) {
        if (code == null || code.isEmpty()) {
            throw new BusinessException(LOGIN_GOOGLE_ERROR);
        }
        GooglePojo googlePojo;
        try {
            String accessToken = googleUtils.getToken(code);
            googlePojo = googleUtils.getUserInfo(accessToken);
        } catch (Exception e) {
            logger.error("Login via Google error:", e);
            throw new BusinessException(LOGIN_GOOGLE_ERROR);
        }
        User user = userRepository.findByEmail(googlePojo.getEmail());
        if (user == null) {
            User newUser = new User();
            newUser.setEmail(googlePojo.getEmail());
            newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new BusinessException(ROLE_NOT_FOUND));
            roles.add(userRole);
            newUser.setRoles(roles);
            newUser.setActive(true);
            newUser.setSubscriber(true);
            newUser.setName(googlePojo.getName());
            userRepository.save(newUser);
            return jwtUtils.generateJwtToken(googlePojo.getEmail());
        } else if (Boolean.TRUE.equals(user.isDeleted())) {
            throw new BusinessException(USER_NOT_ACTIVE);
        } else if (Boolean.FALSE.equals(user.isActive())) {
            user.setActive(true);
            userRepository.save(user);
            return jwtUtils.generateJwtToken(googlePojo.getEmail());
        }
        return null;
    }
}
