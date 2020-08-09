package com.svetikov.ecommerceshop.security;

import com.svetikov.ecommerceshop.model.user.Role;
import com.svetikov.ecommerceshop.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class UserDetailsFactory {

    public UserDetailsFactory() {
    }

    public static UserDetailsImpl create(User user) {


        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getEmail(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles()))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.
                stream().
                map(role -> new SimpleGrantedAuthority(role.getName())).
                collect(Collectors.toList());
    }
}
