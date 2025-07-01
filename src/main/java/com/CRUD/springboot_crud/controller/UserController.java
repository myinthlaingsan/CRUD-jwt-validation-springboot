package com.CRUD.springboot_crud.controller;


import com.CRUD.springboot_crud.pojo.ResponseMessage;
import com.CRUD.springboot_crud.pojo.User;
import com.CRUD.springboot_crud.pojo.dto.UserDto;
import com.CRUD.springboot_crud.service.Impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Operation(summary = "Create new User", description = "Create a new user with given data")
    @PostMapping
    public ResponseMessage<User> add(@Valid @RequestBody UserDto user){
        User userNew = userService.add(user);
        return ResponseMessage.success(userNew);
    }

    @GetMapping("/{userId}")
    public ResponseMessage<User> findById(@PathVariable Integer userId){
        User userNew = userService.getUserById(userId);
        return ResponseMessage.success(userNew);
    }

    @PutMapping("/{userId}")
    public ResponseMessage<User> edit(@PathVariable Integer userId,@RequestBody UserDto user){
        User userNew = userService.edit(userId, user);
        return ResponseMessage.success(userNew);
    }

    @DeleteMapping("/{userId}")
    public ResponseMessage<User> delete(@PathVariable Integer userId){
        userService.delete(userId);
        return ResponseMessage.success();
    }

    @GetMapping
    public ResponseMessage<List<UserDto>> getAllUsers(){
        List<UserDto> users = userService.getallUsers();
        System.out.println("Users: " + users);
        return ResponseMessage.success(users);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return userService.verify(user);
    }

}
