package com.svetikov.ecommerceshop.repository.product;

import com.svetikov.ecommerceshop.model.product.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPhotosRepository extends JpaRepository<ProductImage,Long> {
    List<ProductImage> findAllByProductId (Long id);

}
