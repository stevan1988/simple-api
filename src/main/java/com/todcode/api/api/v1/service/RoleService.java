package com.todcode.api.api.v1.service;

import com.todcode.api.api.v1.dto.Role;
import com.todcode.api.api.v1.dto.type.RoleType;
import com.todcode.api.api.v1.repository.RoleRepository;
import com.todcode.api.web.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public Role findByType(RoleType type) {
        log.info("Find Role by RoleName " + type.toString());
        return repository.findByType(type).orElseThrow(() -> new ApplicationException("User Role not set."));
    }
}
