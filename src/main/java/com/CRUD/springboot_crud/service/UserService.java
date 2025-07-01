package com.CRUD.springboot_crud.service;

import com.CRUD.springboot_crud.pojo.User;
import com.CRUD.springboot_crud.pojo.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserDto> getallUsers();
    User add(UserDto user);
    User getUserById(Integer userId);
    User edit(Integer userId,UserDto user);
    void delete(Integer userId);
    String verify(User user);
}
