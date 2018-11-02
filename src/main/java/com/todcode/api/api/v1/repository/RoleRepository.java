package com.todcode.api.api.v1.repository;

import com.todcode.api.api.v1.dto.Role;
import com.todcode.api.api.v1.dto.type.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByType(RoleType roleType);
}
