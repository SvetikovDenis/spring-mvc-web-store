package com.svetikov.ecommerceshop.service.product;

import com.svetikov.ecommerceshop.dto.model.ProductDto;
import com.svetikov.ecommerceshop.model.product.Product;
import com.svetikov.ecommerceshop.model.product.ProductImage;
import com.svetikov.ecommerceshop.util.ProductListRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Page<Product> findAllPageable(ProductListRequest request, Pageable pageable);

    Product findSingleProduct(Long id);

    ProductDto findSingleProductDto(Long id);

    List<ProductImage> findAllProductImages(Long id);

    Integer findMaxProductPriceByCategory(String category);

    Integer getMaxPrice();

    Integer findMinProductPriceByCategory(String category);

    ProductDto saveProductDto(ProductDto productDto, MultipartFile photoUpload);

    ProductDto updateProductDto(ProductDto product, MultipartFile photoUpload);

    Product saveProduct(Product product);

    String setProductActivation(Long id);

    String setProductIsNew(Long id);

    Boolean deleteProduct(Long id);
}
