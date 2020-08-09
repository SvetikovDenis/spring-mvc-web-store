package com.svetikov.ecommerceshop.repository.user;

import com.svetikov.ecommerceshop.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role getRoleById(Long id);

    Role getRoleByName(String name);

}
