package com.keygame.service;

import com.keygame.dto.*;
import com.keygame.dto.response.SimpleUserResponse;
import com.keygame.dto.response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserService {

  boolean updatePassword(String password, HttpServletRequest req) ;

  Boolean createUser(RegistryDto registryDto);

  List<UserResponse> getListUser();

  boolean delete(Long id);

  SimpleUserResponse getProfile(HttpServletRequest req);
}
