package com.CRUD.springboot_crud.config;

import com.CRUD.springboot_crud.pojo.User;
import com.CRUD.springboot_crud.pojo.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(){

        return new ModelMapper();
    }
}
