package com.keygame.config.security;

import com.keygame.config.exceptionhandler.BusinessException;
import com.keygame.entity.User;
import com.keygame.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.keygame.common.ErrorConstant.USER_NOT_ACTIVE;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    final User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new BusinessException("User with email: " + email + " not found");
    }

    if (!user.isActive()) {
      throw new BusinessException(USER_NOT_ACTIVE);
    }

    return UserDetailsImpl.build(user);
  }
}
