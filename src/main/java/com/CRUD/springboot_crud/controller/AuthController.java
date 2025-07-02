package com.CRUD.springboot_crud.controller;

import com.CRUD.springboot_crud.pojo.User;
import com.CRUD.springboot_crud.service.Impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final UserServiceImpl userService;

    public AuthController(UserServiceImpl userService){
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return userService.verify(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response){
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout Successfully");
    }

}
