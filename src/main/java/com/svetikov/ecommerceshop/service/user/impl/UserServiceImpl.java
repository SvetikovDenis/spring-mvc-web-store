package com.svetikov.ecommerceshop.service.user.impl;

import com.svetikov.ecommerceshop.model.user.User;
import com.svetikov.ecommerceshop.repository.user.UserRepository;
import com.svetikov.ecommerceshop.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {


    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository repository;

    @Override
    public List<User> getAllUsers() {
        List<User> users = repository.findAll();
        if (users.isEmpty()) {
            log.warn("In getAllUsers - no users was found");
        }
        log.info("In getAllUsers - {} : users was found", users.size());
        return users;
    }
    @Override
    public User getUserByEmail(String email)  {

        User user = repository.getUserByEmail(email);
        if (user == null) {
            log.warn("In getUserByEmail - no users was found with email : {}", email);
        }
        log.info("In getUserByEmail - user with email : {} was found ",email);
        return user;
    }

    @Override
    public User getUserByUserName(String username) {
        User user = repository.getUserByUsername(username);
        if (user == null) {
            log.warn("In getUserByUserName - no users was found with username : {}", username);
        }
        log.info("In getUserByUserName - user with username : {} was found ",username);
        return user;
    }

    @Override
    public User getUserByUserId(Long id) {
        User user = repository.getUserById(id);
        if (user == null) {
            log.warn("In getUserByUserId - no users was found with id : {}", id);
        }
        log.info("In getUserByUserId - user with id : {} was found ",id);
        return user;
    }

    @Override
    public User saveUser(User user) {
        return repository.save(user);
    }
}
