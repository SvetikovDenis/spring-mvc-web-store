package com.svetikov.ecommerceshop.service.user;

import com.svetikov.ecommerceshop.model.user.Role;

public interface RoleService {

    Role getRoleByName(String name);

    Role getRoleById(Long id);

}
