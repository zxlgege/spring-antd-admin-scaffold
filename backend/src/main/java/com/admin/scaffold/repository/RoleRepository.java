package com.admin.scaffold.repository;

import com.admin.scaffold.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByCode(String code);

    Set<Role> findByCodeIn(Set<String> codes);

    boolean existsByCode(String code);

    boolean existsByName(String name);
}
