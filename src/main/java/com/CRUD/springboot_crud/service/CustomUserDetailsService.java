package com.CRUD.springboot_crud.service;

import com.CRUD.springboot_crud.pojo.User;
import com.CRUD.springboot_crud.pojo.UserPrincipal;
import com.CRUD.springboot_crud.repository.UserRepositroy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepositroy userRepositroy;

    public CustomUserDetailsService(UserRepositroy userRepositroy) {
        this.userRepositroy = userRepositroy;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepositroy.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserPrincipal(user);
    }

}
