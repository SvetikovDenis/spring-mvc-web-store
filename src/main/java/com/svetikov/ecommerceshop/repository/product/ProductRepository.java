package com.svetikov.ecommerceshop.repository.product;

import com.svetikov.ecommerceshop.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> , JpaSpecificationExecutor<Product> {

    @Query(value = "select max(unit_price) from product",nativeQuery = true)
    Integer getProductMaxUnitPrice();

    @Query(value = "select max(unitPrice) from Product p where p.category = (select c.id from Category c where c.webUrl = ?1)")
    Integer findMaxProductPriceByCategory(String categoryUrl);

    @Query(value = "select min(unitPrice) from Product p where p.category = (select c.id from Category c where c.webUrl = ?1)")
    Integer findMinProductPriceByCategory(String categoryUrl);

    @Query(value = "select max(unitPrice) from Product p")
    Integer findMaxProductPrice();

    @Query(value = "select min(unitPrice) from Product p")
    Integer findMinProductPrice();

    Product findProductById(Long id);

}
