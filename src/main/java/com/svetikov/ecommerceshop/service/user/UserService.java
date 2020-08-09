package com.svetikov.ecommerceshop.service.user;

import com.svetikov.ecommerceshop.model.user.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserByEmail(String email);

    User getUserByUserName(String username);

    User getUserByUserId(Long id);

    User saveUser(User user);

}
