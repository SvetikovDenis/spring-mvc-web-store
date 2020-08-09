package com.svetikov.ecommerceshop.dto.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
public class ProductBrandDto extends AbstractDto {

    @NotBlank(groups = {Create.class, Existing.class}, message = "Name should be not null or blank")
    @JsonView({StandardView.class, DetailsView.class})
    private String name;


    public ProductBrandDto() {
    }

    @Builder
    public ProductBrandDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }


}
