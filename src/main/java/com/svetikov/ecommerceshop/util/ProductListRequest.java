package com.svetikov.ecommerceshop.util;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductListRequest {

    private String category;
    private String search;
    private Integer priceF;
    private Integer priceT;
    private String brand;
    private String active;
    private String discount;
    private String sort;
    private String order;

}
