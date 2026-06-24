package com.admin.scaffold.config;

import com.admin.scaffold.entity.Role;
import com.admin.scaffold.entity.User;
import com.admin.scaffold.repository.RoleRepository;
import com.admin.scaffold.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitConfig {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // ---- Roles ----
            Role adminRole = roleRepository.findByCode("ADMIN").orElseGet(() ->
                    roleRepository.save(Role.builder()
                            .name("\u8d85\u7ea7\u7ba1\u7406\u5458").code("ADMIN")
                            .description("Full system access").build()));

            Role operatorRole = roleRepository.findByCode("OPERATOR").orElseGet(() ->
                    roleRepository.save(Role.builder()
                            .name("\u8fd0\u8425\u4eba\u5458").code("OPERATOR")
                            .description("Operation management access").build()));

            Role viewerRole = roleRepository.findByCode("VIEWER").orElseGet(() ->
                    roleRepository.save(Role.builder()
                            .name("\u53ea\u8bfb\u7528\u6237").code("VIEWER")
                            .description("Read-only access").build()));

            // ---- Users ----
            if (!userRepository.existsByUsername("admin")) {
                Set<Role> adminRoles = new HashSet<>();
                adminRoles.add(adminRole);
                userRepository.save(User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin123"))
                        .nickname("\u8d85\u7ea7\u7ba1\u7406\u5458")
                        .email("admin@example.com")
                        .status(1).roles(adminRoles).build());
            }

            if (!userRepository.existsByUsername("operator")) {
                Set<Role> opRoles = new HashSet<>();
                opRoles.add(operatorRole);
                userRepository.save(User.builder()
                        .username("operator")
                        .password(passwordEncoder.encode("operator123"))
                        .nickname("\u8fd0\u8425\u4eba\u5458")
                        .email("operator@example.com")
                        .status(1).roles(opRoles).build());
            }

            if (!userRepository.existsByUsername("viewer")) {
                Set<Role> viewRoles = new HashSet<>();
                viewRoles.add(viewerRole);
                userRepository.save(User.builder()
                        .username("viewer")
                        .password(passwordEncoder.encode("viewer123"))
                        .nickname("\u53ea\u8bfb\u7528\u6237")
                        .email("viewer@example.com")
                        .status(1).roles(viewRoles).build());
            }

            log.info("\u2705 Admin scaffold initialized: admin/admin123 | operator/operator123 | viewer/viewer123");
        };
    }
}
