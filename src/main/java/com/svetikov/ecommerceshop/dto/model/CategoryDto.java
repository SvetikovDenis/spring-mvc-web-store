package com.svetikov.ecommerceshop.dto.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
public class CategoryDto extends AbstractDto {

    @NotBlank(groups = {Create.class, Existing.class}, message = "Name should be not null or blank")
    @JsonView({StandardView.class, DetailsView.class})
    private String name;

    @NotBlank(groups = {Create.class, Existing.class},message = "Image code should be not null or blank")
    @JsonView({StandardView.class, DetailsView.class})
    private String imageCode;

    @NotBlank(groups = {Create.class, Existing.class},message = "Description should be not null or blank")
    @JsonView({StandardView.class, DetailsView.class})
    private String description;

    @Null(groups = Create.class)
    @NotNull(groups = Existing.class)
    @JsonView(DetailsView.class)
    private Boolean isActive;


    public CategoryDto() {
    }

    @Builder
    public CategoryDto(Long id,String name,String imageCode, String description, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.imageCode = imageCode;
        this.description = description;
        this.isActive = isActive;
    }
}
