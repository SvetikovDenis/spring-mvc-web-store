package com.svetikov.ecommerceshop.model.product;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "category")
@Data
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "web_url")
    private String webUrl;

    @NotBlank
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "image_code")
    private String imageCode;

    @Column(name = "is_active")
    private boolean isActive;

    @PrePersist
    private void toCreate() {
        setActive(true);
    }
}
