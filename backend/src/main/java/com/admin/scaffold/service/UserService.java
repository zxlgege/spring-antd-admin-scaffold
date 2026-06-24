package com.admin.scaffold.service;

import com.admin.scaffold.common.PageResult;
import com.admin.scaffold.dto.UserCreateRequest;
import com.admin.scaffold.dto.UserDTO;
import com.admin.scaffold.dto.UserUpdateRequest;

public interface UserService {

    PageResult<UserDTO> listUsers(int page, int pageSize, String keyword, Integer status);

    UserDTO getUserById(Long id);

    UserDTO createUser(UserCreateRequest request);

    UserDTO updateUser(Long id, UserUpdateRequest request);

    void deleteUser(Long id);

    void updateStatus(Long id, Integer status);

    long countUsers();

    long countUsersByStatus(Integer status);
}
