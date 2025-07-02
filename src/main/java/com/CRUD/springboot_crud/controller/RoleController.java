package com.CRUD.springboot_crud.controller;

import com.CRUD.springboot_crud.pojo.ResponseMessage;
import com.CRUD.springboot_crud.pojo.dto.RoleDto;
import com.CRUD.springboot_crud.service.Impl.RoleServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/role")

public class RoleController {
    private final RoleServiceImpl roleService;

    public RoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseMessage add(@RequestBody RoleDto roleDto){
        roleService.add(roleDto);
        return ResponseMessage.success(roleDto);
    }

    @GetMapping
    public ResponseMessage getAllRoles(){
        List<RoleDto> roles = roleService.getAllRoles();
        return ResponseMessage.success(roles);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{roleId}")
    public ResponseMessage edit(@PathVariable Integer roleId, @RequestBody RoleDto roleDto){
        RoleDto role = roleService.update(roleId,roleDto);
        return ResponseMessage.success(role);
    }

    @DeleteMapping("/{roleId}")
    public ResponseMessage delete(@PathVariable Integer roleId){
        roleService.delete(roleId);
        return ResponseMessage.success();
    }

    @GetMapping("/{roleId}")
    public ResponseMessage getRoleById(@PathVariable Integer roleId){
        RoleDto roleDto = roleService.getRoleById(roleId);
        return ResponseMessage.success(roleDto);
    }

}
