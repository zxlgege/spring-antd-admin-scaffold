package com.admin.scaffold.service.impl;

import com.admin.scaffold.common.PageResult;
import com.admin.scaffold.dto.UserCreateRequest;
import com.admin.scaffold.dto.UserDTO;
import com.admin.scaffold.dto.UserUpdateRequest;
import com.admin.scaffold.entity.Role;
import com.admin.scaffold.entity.User;
import com.admin.scaffold.exception.BusinessException;
import com.admin.scaffold.repository.RoleRepository;
import com.admin.scaffold.repository.UserRepository;
import com.admin.scaffold.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PageResult<UserDTO> listUsers(int page, int pageSize, String keyword, Integer status) {
        PageRequest pageable = PageRequest.of(page - 1, pageSize,
                Sort.by(Sort.Direction.DESC, "createdAt"));
        String kw = StringUtils.hasText(keyword) ? keyword : null;
        Page<User> pageResult = userRepository.findByKeywordAndStatus(kw, status, pageable);
        List<UserDTO> records = pageResult.getContent().stream()
                .map(this::toDTO).collect(Collectors.toList());
        return PageResult.of(records, pageResult.getTotalElements(), page, pageSize);
    }

    @Override
    public UserDTO getUserById(Long id) {
        return toDTO(findUser(id));
    }

    @Override
    @Transactional
    public UserDTO createUser(UserCreateRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new BusinessException("Username already exists: " + req.getUsername());
        }
        if (StringUtils.hasText(req.getEmail()) && userRepository.existsByEmail(req.getEmail())) {
            throw new BusinessException("Email already in use: " + req.getEmail());
        }
        User user = User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .nickname(req.getNickname())
                .email(req.getEmail())
                .phone(req.getPhone())
                .avatar(req.getAvatar())
                .status(req.getStatus() != null ? req.getStatus() : 1)
                .roles(resolveRoles(req.getRoles()))
                .build();
        return toDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserUpdateRequest req) {
        User user = findUser(id);
        user.setNickname(req.getNickname());
        if (StringUtils.hasText(req.getEmail())) {
            if (!req.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(req.getEmail())) {
                throw new BusinessException("Email already in use: " + req.getEmail());
            }
            user.setEmail(req.getEmail());
        }
        if (req.getPhone() != null) user.setPhone(req.getPhone());
        if (req.getAvatar() != null) user.setAvatar(req.getAvatar());
        if (req.getStatus() != null) user.setStatus(req.getStatus());
        if (req.getRoles() != null) user.setRoles(resolveRoles(req.getRoles()));
        return toDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new BusinessException(404, "User not found: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateStatus(Long id, Integer status) {
        User user = findUser(id);
        user.setStatus(status);
        userRepository.save(user);
    }

    @Override
    public long countUsers() {
        return userRepository.count();
    }

    @Override
    public long countUsersByStatus(Integer status) {
        return userRepository.countByStatus(status);
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "User not found: " + id));
    }

    private Set<Role> resolveRoles(Set<String> roleCodes) {
        if (roleCodes == null || roleCodes.isEmpty()) return new HashSet<>();
        return new HashSet<>(roleRepository.findByCodeIn(roleCodes));
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
        dto.setRoles(user.getRoles().stream()
                .map(Role::getCode).collect(Collectors.toSet()));
        return dto;
    }
}
