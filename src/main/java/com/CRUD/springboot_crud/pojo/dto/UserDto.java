package com.CRUD.springboot_crud.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Schema(description = "User's login name")
    @NotBlank(message = "User name required")
    private String userName;

    @NotBlank(message = "password required")
    @Length(min = 6,max = 12)
    private String password;

    @NotBlank(message = "email required")
    private String email;

    private Set<Integer> roleId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Integer> getRoleId() {
        return roleId;
    }

    public void setRoleId(Set<Integer> roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
