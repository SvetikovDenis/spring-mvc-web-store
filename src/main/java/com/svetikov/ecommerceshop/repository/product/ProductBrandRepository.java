package com.svetikov.ecommerceshop.repository.product;

import com.svetikov.ecommerceshop.model.product.ProductBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBrandRepository extends JpaRepository<ProductBrand,Long> {
    ProductBrand findProductBrandById(Long id);
}
