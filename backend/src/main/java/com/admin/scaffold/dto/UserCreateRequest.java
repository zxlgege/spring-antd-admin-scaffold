package com.admin.scaffold.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class UserCreateRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be 3-50 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Nickname is required")
    @Size(max = 50, message = "Nickname must be at most 50 characters")
    private String nickname;

    @Email(message = "Invalid email format")
    private String email;

    private String phone;
    private String avatar;
    private Integer status;
    private Set<String> roles;
}
