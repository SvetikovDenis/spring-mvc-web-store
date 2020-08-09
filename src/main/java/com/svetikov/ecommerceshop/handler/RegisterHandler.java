package com.svetikov.ecommerceshop.handler;

import com.svetikov.ecommerceshop.model.user.Address;
import com.svetikov.ecommerceshop.model.user.RegisterModel;
import com.svetikov.ecommerceshop.model.user.Role;
import com.svetikov.ecommerceshop.model.user.User;
import com.svetikov.ecommerceshop.service.user.AddressService;
import com.svetikov.ecommerceshop.service.user.RoleService;
import com.svetikov.ecommerceshop.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RegisterHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public RegisterModel init() {
        return new RegisterModel();
    }

    public void addUser(RegisterModel registerModel, User user) {
        registerModel.setUser(user);
    }

    public void addAddress(RegisterModel registerModel, Address address) {
        registerModel.setAddress(address);
    }

    public String validateUser(User user, MessageContext error) {
        String transitionValue = "success";

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            error.addMessage(new MessageBuilder().error().source(
                    "confirmPassword").defaultText("Password does not match confirm password!").build());
            transitionValue = "failure";
        }
        if (userService.getUserByEmail(user.getEmail()) != null) {
            error.addMessage(new MessageBuilder().error().source(
                    "email").defaultText("Email address is already taken!").build());
            transitionValue = "failure";
        }

        if (userService.getUserByUserName(user.getUsername()) != null) {
            error.addMessage(new MessageBuilder().error().source(
                    "username").defaultText("Username is already taken!").build());
            transitionValue = "failure";
        }

        return transitionValue;
    }

    public String saveAll(RegisterModel registerModel) {

        String transitionValue = "success";
        User user = registerModel.getUser();

        Role userRole = roleService.getRoleByName("ROLE_USER");
        List<Role> userRoles = Arrays.asList(userRole);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);

        userService.saveUser(user);

        Address address = registerModel.getAddress();

        address.setUserId(user.getId());
        address.setIsBilling(true);
        address.setIsShipping(false);

        addressService.saveAddress(address);

        return transitionValue;
    }

}
