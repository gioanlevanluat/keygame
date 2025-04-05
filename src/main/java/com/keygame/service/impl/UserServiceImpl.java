package com.keygame.service.impl;

import com.keygame.config.exceptionhandler.BusinessException;
import com.keygame.config.security.JwtUtils;
import com.keygame.dto.RegistryDto;
import com.keygame.dto.response.SimpleUserResponse;
import com.keygame.dto.response.UserResponse;
import com.keygame.entity.ERole;
import com.keygame.entity.Role;
import com.keygame.entity.User;
import com.keygame.repository.RoleRepository;
import com.keygame.repository.UserRepository;
import com.keygame.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.keygame.common.ErrorConstant.ROLE_NOT_FOUND;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Boolean createUser(RegistryDto registryDto) {
        User user = new User();
        user.setEmail(registryDto.getEmail());
        user.setPassword(passwordEncoder.encode(registryDto.getPassword()));
        user.setSubscriber(registryDto.isSubscriber());
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new BusinessException(ROLE_NOT_FOUND));
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean updatePassword(String password, HttpServletRequest req) {
        String email = jwtUtils.getEmailFromJwtToken(jwtUtils.parseJwt(req));
        User user = userRepository.findByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return true;
    }

    @Override
    public List<UserResponse> getListUser() {
        return userRepository.findAll().stream().map(user -> {
            return modelMapper.map(user, UserResponse.class);
        }).toList();
    }

    @Override
    public boolean delete(Long id) {
        userRepository.delete(Collections.singletonList(id));
        return true;
    }

    @Override
    public SimpleUserResponse getProfile(HttpServletRequest req) {
        User user = userRepository.findByEmail(jwtUtils.getEmailFromJwtToken(jwtUtils.parseJwt(req)));
        SimpleUserResponse simpleUserResponse = new SimpleUserResponse();
        simpleUserResponse.setEmail(user.getEmail());
        simpleUserResponse.setId(user.getId());
        return simpleUserResponse;
    }
}
