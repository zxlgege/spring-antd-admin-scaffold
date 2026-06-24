package com.admin.scaffold.controller;

import com.admin.scaffold.common.Result;
import com.admin.scaffold.entity.Role;
import com.admin.scaffold.repository.RoleRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Tag(name = "Role Management", description = "APIs for RBAC role management")
public class RoleController {

    private final RoleRepository roleRepository;

    @GetMapping
    @Operation(summary = "List all roles")
    public Result<List<Role>> listRoles() {
        return Result.success(roleRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get role by ID")
    public Result<Role> getRole(@PathVariable Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found: " + id));
        return Result.success(role);
    }

    @PostMapping
    @Operation(summary = "Create a new role")
    public Result<Role> createRole(@RequestBody Role role) {
        return Result.success(roleRepository.save(role));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update role")
    public Result<Role> updateRole(@PathVariable Long id, @RequestBody Role body) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found: " + id));
        role.setName(body.getName());
        role.setDescription(body.getDescription());
        role.setStatus(body.getStatus());
        return Result.success(roleRepository.save(role));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete role")
    public Result<Void> deleteRole(@PathVariable Long id) {
        roleRepository.deleteById(id);
        return Result.success();
    }
}
