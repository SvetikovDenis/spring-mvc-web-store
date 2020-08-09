package com.svetikov.ecommerceshop.model.user;
import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterModel implements Serializable {
    private User user;
    private Address address;
}
