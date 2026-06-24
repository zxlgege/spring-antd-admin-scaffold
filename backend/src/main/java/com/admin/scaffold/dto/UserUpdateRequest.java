package com.admin.scaffold.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class UserUpdateRequest {

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
