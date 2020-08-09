package com.svetikov.ecommerceshop.model.user;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Entity
@Table(name = "role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @Override
    public String toString() {
        return "Role{" +
                "id: " + id + ", " +
                "name: " + name + "}";
    }

    public Role() {
    }

    @Builder
    public Role( String name) {
        this.name = name;
    }

}
