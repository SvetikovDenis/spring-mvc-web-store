package com.svetikov.ecommerceshop.service.product.impl;

import com.svetikov.ecommerceshop.exception.EntityNotFoundException;
import com.svetikov.ecommerceshop.model.product.ProductBrand;
import com.svetikov.ecommerceshop.repository.product.ProductBrandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductBrandService {

    @Autowired
    private ProductBrandRepository productBrandRepository;

    private final Logger log = LoggerFactory.getLogger(ProductBrandService.class);

    public List<ProductBrand> findAllProductBrands(){

        List<ProductBrand> productBrands = productBrandRepository.findAll();
        if (productBrands.isEmpty()) {
            log.warn("In findAllProductBrands - not product brands was found");
            new EntityNotFoundException(ProductBrand.class, "all", "find all");
        }
        log.info("In findAllProductBrands - {} product brands was found",productBrands.size());
        return productBrands;
    }

    public ProductBrand findById(Long id) {

        ProductBrand productBrand = productBrandRepository.findProductBrandById(id);
        if (productBrand == null) {
            log.warn("In findById - not product brand was found with id : {}",productBrand);
            new EntityNotFoundException(ProductBrand.class, "id", id.toString());
        }
        log.info("In findById - product brand with id : {} was found ", id);
        return productBrand;
    }


}
