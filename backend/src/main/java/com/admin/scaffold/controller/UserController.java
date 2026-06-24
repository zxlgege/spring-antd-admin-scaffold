package com.admin.scaffold.controller;

import com.admin.scaffold.common.PageResult;
import com.admin.scaffold.common.Result;
import com.admin.scaffold.dto.UserCreateRequest;
import com.admin.scaffold.dto.UserDTO;
import com.admin.scaffold.dto.UserUpdateRequest;
import com.admin.scaffold.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "CRUD APIs for system users")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "List users with pagination and filters")
    public Result<PageResult<UserDTO>> listUsers(
            @Parameter(description = "Page number (1-based)") @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Page size")             @RequestParam(defaultValue = "10") int pageSize,
            @Parameter(description = "Keyword search")        @RequestParam(required = false) String keyword,
            @Parameter(description = "Status filter 0/1")     @RequestParam(required = false) Integer status) {
        return Result.success(userService.listUsers(page, pageSize, keyword, status));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    public Result<UserDTO> getUser(@PathVariable Long id) {
        return Result.success(userService.getUserById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new user")
    public Result<UserDTO> createUser(@Valid @RequestBody UserCreateRequest request) {
        return Result.success(userService.createUser(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update user information")
    public Result<UserDTO> updateUser(@PathVariable Long id,
                                      @Valid @RequestBody UserUpdateRequest request) {
        return Result.success(userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user by ID")
    public Result<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return Result.success();
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Enable or disable a user")
    public Result<Void> updateStatus(@PathVariable Long id,
                                     @RequestBody Map<String, Integer> body) {
        userService.updateStatus(id, body.get("status"));
        return Result.success();
    }
}
