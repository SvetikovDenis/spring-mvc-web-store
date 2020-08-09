package com.svetikov.ecommerceshop.model.product;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Image code should not be null or blank")
    @Column(name = "image_code")
    private String imageCode;

    @NotBlank(message = "Product name should not be null or blank")
    @Size(min = 5,max = 50)
    @Column(name = "name")
    private String name;

    @NotNull
    @OneToOne
    @JoinColumn(name = "brand_id")
    private ProductBrand brand;

    @NotBlank(message = "Product description should not be null or blank")
    @Size(min = 5,max = 255)
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "is_active")
    private Boolean isActive;

    @NotNull
    @Column(name = "is_new")
    private Boolean isNew;

    @NotNull
    @Column(name = "date_receipt")
    private Timestamp dateReceipt;

    @NotNull(message = "Unit price should no be not null")
    @Column(name = "unit_price")
    private Integer unitPrice;

    @NotNull(message = "Quantity should no be not null")
    @Column(name = "quantity")
    private Integer quantity;

    @NotNull(message = "discount should be not null")
    @Column(name = "discount")
    private Integer discount;

    @NotNull
    @OneToOne
    private Category category;

    @NotNull
    @Column(name = "purchases")
    private Integer purchases;

    @NotNull
    @Column(name = "views")
    private Integer views;

    @PrePersist
    private void toCreate() {
        setDateReceipt(new Timestamp(System.currentTimeMillis()));
        setIsActive(true);
        setIsNew(true);
        setPurchases(0);
        setViews(0);
    }

}

