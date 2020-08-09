package com.svetikov.ecommerceshop.dto.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProductDto extends AbstractDto {

    @JsonView({StandardView.class, DetailsView.class})
    private String imageCode;

    @NotBlank(groups = {Create.class, Update.class}, message = "Name should be not null or blank")
    @JsonView({StandardView.class, DetailsView.class})
    private String name;

    @NotNull(groups = {Create.class, Update.class}, message = "Brand id should be not null")
    @JsonView({StandardView.class, DetailsView.class})
    private Long brandId;

    @NotBlank(groups = {Create.class, Update.class}, message = "Description should be not null or blank")
    @JsonView({StandardView.class, DetailsView.class})
    private String description;

    @Null(groups = Create.class)
    @JsonView(DetailsView.class)
    private Timestamp dateReceipt;

    @Null(groups = Create.class)
    @JsonView(DetailsView.class)
    private Boolean isActive;

    @Null(groups = Create.class)
    @JsonView(DetailsView.class)
    private Boolean isNew;

    @NotNull(groups = {Create.class, Update.class}, message = "Unit price should be not null")
    @JsonView({StandardView.class, DetailsView.class})
    private Integer unitPrice;

    @NotNull(groups = {Create.class, Update.class}, message = "Quantity should be not null")
    @JsonView({StandardView.class, DetailsView.class})
    private Integer quantity;

    @NotNull(groups = {Create.class, Update.class}, message = "Discount should be not null")
    @JsonView({StandardView.class, DetailsView.class})
    private Integer discount;

    @NotNull(groups = {Create.class, Update.class}, message = "Category id should be not null")
    @JsonView({StandardView.class, DetailsView.class})
    private Long categoryId;


    public ProductDto() {
    }

    @Builder
    public ProductDto(Long id, String imageCode, String name, Long brandId, String description, Timestamp dateReceipt, Boolean isActive, Boolean isNew, Integer unitPrice, Integer discount, Long categoryId) {
        this.id = id;
        this.imageCode = imageCode;
        this.name = name;
        this.brandId = brandId;
        this.description = description;
        this.dateReceipt = dateReceipt;
        this.isActive = isActive;
        this.isNew = isNew;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.categoryId = categoryId;
    }

}
