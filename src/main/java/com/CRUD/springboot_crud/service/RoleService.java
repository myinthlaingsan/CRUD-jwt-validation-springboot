package com.CRUD.springboot_crud.service;

import com.CRUD.springboot_crud.pojo.dto.RoleDto;

import java.util.List;

public interface RoleService {
    RoleDto add(RoleDto roleDto);
    List<RoleDto> getAllRoles();
    RoleDto getRoleById(Integer roleId);
    RoleDto update(Integer roleId,RoleDto roleDto);
    void delete(Integer roleId);
}
