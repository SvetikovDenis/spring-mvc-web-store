package com.svetikov.ecommerceshop.security;

import com.svetikov.ecommerceshop.model.user.User;
import com.svetikov.ecommerceshop.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    private final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.getUserByUserName(username);
        if (user == null) {
            log.warn("In loadUserByUsername - user with username - " + username + " not found");
            throw new UsernameNotFoundException("User with username - " + username + " not found");
        }

        log.info("In loadUserByUsername - user with username " + username + " was found");

        UserDetailsImpl userDetails = UserDetailsFactory.create(user);
        return userDetails;
    }
}
