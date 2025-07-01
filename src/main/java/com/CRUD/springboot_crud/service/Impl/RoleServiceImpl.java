package com.CRUD.springboot_crud.service.Impl;

import com.CRUD.springboot_crud.exception.ResourceNotFoundException;
import com.CRUD.springboot_crud.pojo.Role;
import com.CRUD.springboot_crud.pojo.dto.RoleDto;
import com.CRUD.springboot_crud.repository.RoleRepository;
import com.CRUD.springboot_crud.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RoleDto add(RoleDto roleDto) {
        Role role = modelMapper.map(roleDto,Role.class);
        Role saveRole = roleRepository.save(role);
        return modelMapper.map(saveRole,RoleDto.class);
//        return modelMapper.map(roleRepository.save(modelMapper.map(roleDto, Role.class)), RoleDto.class);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(role->modelMapper.map(role, RoleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoleDto getRoleById(Integer roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(()->new ResourceNotFoundException("RoleId not found" + roleId));
        return modelMapper.map(role,RoleDto.class);
    }

    @Override
    public RoleDto update(Integer roleId, RoleDto roleDto) {
        Role existingRole = roleRepository.findById(roleId).orElseThrow(()->new ResourceNotFoundException("Role not found with Id" + roleId));
        modelMapper.map(roleDto,existingRole);
        Role saveNewRole = roleRepository.save(existingRole);
        return modelMapper.map(saveNewRole,RoleDto.class);
    }

    @Override
    public void delete(Integer roleId) {
        roleRepository.deleteById(roleId);
    }
}
