package com.svetikov.ecommerceshop.repository.user;

import com.svetikov.ecommerceshop.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User getUserByEmail(String email);

    User getUserById(Long id);

    User getUserByUsername(String username);

}
