package com.admin.scaffold.controller;

import com.admin.scaffold.common.Result;
import com.admin.scaffold.dto.LoginRequest;
import com.admin.scaffold.dto.LoginResponse;
import com.admin.scaffold.dto.UserDTO;
import com.admin.scaffold.entity.User;
import com.admin.scaffold.entity.Role;
import com.admin.scaffold.repository.UserRepository;
import com.admin.scaffold.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Login / Logout / Profile APIs")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate with username and password, returns JWT token")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateToken(request.getUsername());
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        UserDTO userDTO = toDTO(user);
        return Result.success(new LoginResponse(token, userDTO));
    }

    @GetMapping("/profile")
    @Operation(summary = "Get current user profile")
    public Result<UserDTO> profile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        return Result.success(toDTO(user));
    }

    @PostMapping("/logout")
    @Operation(summary = "User logout")
    public Result<Void> logout() {
        SecurityContextHolder.clearContext();
        return Result.success();
    }

    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setNickname(user.getNickname());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setAvatar(user.getAvatar());
        dto.setStatus(user.getStatus());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setRoles(user.getRoles().stream().map(Role::getCode).collect(Collectors.toSet()));
        return dto;
    }
}
