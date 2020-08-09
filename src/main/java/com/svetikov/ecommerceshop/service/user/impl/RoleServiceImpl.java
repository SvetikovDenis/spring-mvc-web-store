package com.svetikov.ecommerceshop.service.user.impl;

import com.svetikov.ecommerceshop.exception.EntityNotFoundException;
import com.svetikov.ecommerceshop.model.user.Role;
import com.svetikov.ecommerceshop.repository.user.RoleRepository;
import com.svetikov.ecommerceshop.service.user.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    private final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Override
    public Role getRoleByName(String name) {
        Role role = roleRepository.getRoleByName(name);
        if (role == null) {
            log.warn("In getRoleByName - no role was found with name : {}", name);
            throw new EntityNotFoundException(Role.class, "name", name);
        }
        return role;
    }

    @Override
    public Role getRoleById(Long id) {
        Role role = roleRepository.getRoleById(id);
        if (role == null) {
            log.warn("In getRoleById - no role was found with id : {}", id);
            throw new EntityNotFoundException(Role.class, "id", id.toString());
        }
        return role;
    }
}
