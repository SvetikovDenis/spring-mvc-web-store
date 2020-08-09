package com.svetikov.ecommerceshop.model.user;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "address")
@Data
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @NotBlank(message = "Please enter address line one!")
    @Column(name = "address_line_one")
    private String addressLineOne;

    @Column(name = "address_line_two")
    private String addressLineTwo;

    @Column(name ="city")
    @NotBlank(message = "Please enter City!")
    private String city;

    @Column(name ="state")
    @NotBlank(message = "Please enter State!")
    private String state;

    @Column(name ="country")
    @NotBlank(message = "Please enter country!")
    private String country;

    @Column(name ="postal_code")
    @NotBlank(message = "Please enter Postal Code!")
    private String postalCode;

    @Column(name="is_shipping")
    private Boolean isShipping;

    @Column(name="is_billing")
    private Boolean isBilling;

    public Address() {
    }

    @Builder
    public Address(Long id, Long userId,String addressLineOne, String addressLineTwo, String city, String state,  String country,  String postalCode, Boolean isShipping, Boolean isBilling) {
        this.id = id;
        this.userId = userId;
        this.addressLineOne = addressLineOne;
        this.addressLineTwo = addressLineTwo;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
        this.isShipping = isShipping;
        this.isBilling = isBilling;
    }
}
