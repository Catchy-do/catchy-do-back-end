package com.main.catchy.security.services;

import com.main.catchy.model.User;
import com.main.catchy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String UserName) throws UsernameNotFoundException {
    User user = userRepository.findUserByUserName(UserName);
    if(user==null){
    new UsernameNotFoundException("User Not Found with username: " + UserName);
    }

    return UserDetailsImpl.build(user);
  }

}
