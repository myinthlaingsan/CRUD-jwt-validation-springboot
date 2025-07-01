package com.CRUD.springboot_crud.service.Impl;

import com.CRUD.springboot_crud.exception.DuplicateUserException;
import com.CRUD.springboot_crud.exception.ResourceNotFoundException;
import com.CRUD.springboot_crud.pojo.Role;
import com.CRUD.springboot_crud.pojo.User;
import com.CRUD.springboot_crud.pojo.dto.UserDto;
import com.CRUD.springboot_crud.repository.RoleRepository;
import com.CRUD.springboot_crud.repository.UserRepositroy;
import com.CRUD.springboot_crud.service.JWTService;
import com.CRUD.springboot_crud.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;
    private final UserRepositroy userRepositroy;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepositroy userRepositroy, RoleRepository roleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepositroy = userRepositroy;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User add(UserDto userDto) {
//        User userPojo = new User();
//        BeanUtils.copyProperties(user,userPojo); // Quick, simple	Can be unsafe, slower
        if(userRepositroy.existsByUserName(userDto.getUserName())){
            throw new DuplicateUserException("Username already exists " + userDto.getUserName());
        }
        User userPojo = modelMapper.map(userDto, User.class);
        userPojo.setPassword(passwordEncoder.encode(userPojo.getPassword()));
        Set<Role> roles = roleRepository.findAllById(userDto.getRoleId()).stream().collect(Collectors.toSet());
        userPojo.setRoles(roles);
        return userRepositroy.save(userPojo);
    }

    @Override
    public User getUserById(Integer userId) {
        return userRepositroy.findById(userId).orElseThrow(() -> {
            throw new ResourceNotFoundException("User Not found with Id "+ userId);
        });
    }

    @Override
    public User edit(Integer userId, UserDto userDto) {
        User existingUser = userRepositroy.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id" + userId));
//        existingUser.setPassword(passwordEncoder.encode(existingUser.getPassword()));
        // Configure modelMapper to skip userId
        modelMapper.typeMap(UserDto.class, User.class)
                .addMappings(mapper -> mapper.skip(User::setUserId));
        modelMapper.map(userDto, existingUser);
        Set<Role> roles = roleRepository.findAllById(userDto.getRoleId()).stream().collect(Collectors.toSet());
        existingUser.setRoles(roles);
        return userRepositroy.save(existingUser);
    }

    @Override
    public void delete(Integer userId) {
        userRepositroy.deleteById(userId);
    }

    @Override
    @Transactional
    public List<UserDto> getallUsers() {
        List<User> users = userRepositroy.findAll();
//        return users.stream()
//                .map(user->modelMapper.map(user, UserDto.class))
//                .collect(Collectors.toList());
        return users.stream().map(user -> {
            UserDto userDto = modelMapper.map(user, UserDto.class);
            Set<Integer> roleId = user.getRoles()
                    .stream()
                    .map(Role::getRoleId)
                    .collect(Collectors.toSet());
            userDto.setRoleId(roleId);
            return userDto;
        }).collect(Collectors.toList());
    }

    @Override
    public String verify (@RequestBody User user){
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
        if(authentication.isAuthenticated()){
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return jwtService.genetareToken(userDetails);
        }
        return "fail";
    }
}
