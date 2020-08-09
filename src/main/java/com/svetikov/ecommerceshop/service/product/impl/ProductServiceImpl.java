package com.svetikov.ecommerceshop.service.product.impl;

import com.svetikov.ecommerceshop.dto.model.ProductDto;
import com.svetikov.ecommerceshop.exception.EntityNotFoundException;
import com.svetikov.ecommerceshop.mapper.ProductMapper;
import com.svetikov.ecommerceshop.model.product.Product;
import com.svetikov.ecommerceshop.model.product.ProductImage;
import com.svetikov.ecommerceshop.repository.product.ProductPhotosRepository;
import com.svetikov.ecommerceshop.repository.product.ProductRepository;
import com.svetikov.ecommerceshop.service.product.ProductService;
import com.svetikov.ecommerceshop.specification.ProductListSpecification;
import com.svetikov.ecommerceshop.util.FileUploadService;
import com.svetikov.ecommerceshop.util.ProductListRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPhotosRepository productPhotosRepository;

    @Autowired
    private ProductListSpecification productListSpecification;

    @Autowired
    private ProductMapper productMapper;

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Override
    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();
        log.info("In findAll - found : {} products", products.size());
        return products;
    }

    @Override
    public Integer getMaxPrice() {
        Integer maxPrice = productRepository.getProductMaxUnitPrice();
        log.info("In getMaxPrice - max product price is : {}", maxPrice);
        return maxPrice;
    }

    @Override
    public Page<Product> findAllPageable(ProductListRequest request, Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(productListSpecification.getFilter(request), pageable);
        log.info("In findAllPageable - get product page number : {} for search request : {} , total products : {}", productPage.getNumber(), request, productPage.getTotalElements());
        return productPage;
    }

    @Override
    public Product findSingleProduct(Long id) {
        Product product = productRepository.findProductById(id);
        log.info("In findSingleProduct - product with id : {} was found ", id);
        return product;
    }

    public ProductDto findSingleProductViewDto(Long id) {
        Product product = productRepository.findProductById(id);
        product.setViews(product.getViews() + 1);
        productRepository.save(product);
        log.info("In findSingleProductViewDto - product with id : {} was found ", id);
        return productMapper.toDto(product);
    }



    @Override
    public ProductDto findSingleProductDto(Long id) throws EntityNotFoundException {
        Product product = productRepository.findProductById(id);
        if (product == null) {
            log.warn("In getAllByUserId - No product was found for with id : {}", id);
            throw new EntityNotFoundException(Product.class, "product id", id.toString());
        }
        log.info("In findSingleProductDto - product with id : {} was found ", id);
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductImage> findAllProductImages(Long productId) {
        List<ProductImage> productImages = productPhotosRepository.findAllByProductId(productId);
        log.info("In findAllProductImages - {} product photos was found for product with id : {} ", productImages.size(), productId);
        return productImages;
    }

    @Override
    public Integer findMaxProductPriceByCategory(String category) {
        Integer maxPrice;
        if (category == null) {
            maxPrice = productRepository.findMaxProductPrice();
            log.info("In findMaxProductPriceByCategory - product max price is : {} ", maxPrice);
            return maxPrice;
        }
        maxPrice = productRepository.findMaxProductPriceByCategory(category);
        log.info("In findMaxProductPriceByCategory - product max price is : {} fore category : {} ", maxPrice,category);
        return maxPrice;
    }

    @Override
    public Integer findMinProductPriceByCategory(String category) {
        Integer minPrice;
        if (category == null) {
            minPrice = productRepository.findMinProductPrice();
            log.info("In findMinProductPriceByCategory - product min price is : {} ", minPrice);
            return minPrice;
        }
        minPrice = productRepository.findMinProductPriceByCategory(category);
        log.info("In findMinProductPriceByCategory - product min price is : {} for category : {}", minPrice,category);
        return minPrice;
    }

    @Override
    public ProductDto saveProductDto(ProductDto productDto, MultipartFile photoUpload) {
        Long productId = productDto.getId();
        if (photoUpload.isEmpty()) {
            log.warn("In saveProductDto - missing product photo for product with id : {}", productId);
            throw new IllegalArgumentException("Missing product photo");
        }
        String imageCode = FileUploadService.uploadFile(photoUpload);
        productDto.setImageCode(imageCode);
        Product newProduct = productMapper.toEntity(productDto);
        productRepository.save(newProduct);
        log.info("In saveProductDto - product with id : {} was saved", productId);
        return productMapper.toDto(newProduct);

    }

    @Override
    public ProductDto updateProductDto(ProductDto product, MultipartFile photoUpload) {
        Long productId = product.getId();
        Product productCandidate = productRepository.findProductById(productId);
        if (productCandidate == null) {
            log.warn("In updateProductDto - No product was found with id: {}", productId);
            throw new EntityNotFoundException(Product.class, "product id", productId.toString());
        }
        log.info("In updateProductDto - product with id : {} was found ", productId);
        productMapper.updateEntity(product, productCandidate);
        if (!photoUpload.isEmpty()) {
            String imageCode = FileUploadService.uploadFile(photoUpload);
            productCandidate.setImageCode(imageCode);
        }
        productRepository.save(productCandidate);
        log.info("In updateProductDto - product with id : {} was updated ", productId);

        return productMapper.toDto(productCandidate);
    }


    @Override
    public Product saveProduct(Product productCandidate) {
        if (productCandidate == null) {
            log.warn("In saveProduct - Null product can'b be saved");
            throw new IllegalArgumentException("Product can't be null");
        }

        Product product = productRepository.save(productCandidate);
        log.info("In saveProduct - product with id : {} was saved", product.getId());
        return product;
    }

    @Override
    public String setProductActivation(Long id) throws EntityNotFoundException {
        Product product = productRepository.findProductById(id);
        if (product == null) {
            log.warn("In setProductActivation - product with id : {} was not found", id);
            throw new EntityNotFoundException(Product.class, "product id", id.toString());
        }
        boolean isActive = product.getIsActive();
        product.setIsActive(!isActive);
        productRepository.save(product);
        log.info("In setProductActivation - product with id : {} was set active to : {}", id, !isActive);
        return (isActive) ? "Product Deactivated Successfully!" : "Product Activated Successfully";
    }

    @Override
    public String setProductIsNew(Long id) throws EntityNotFoundException {
        Product product = productRepository.findProductById(id);
        if (product == null) {
            log.warn("In setProductIsNew - product with id : {} was not found", id);
            throw new EntityNotFoundException(Product.class, "product id", id.toString());
        }
        boolean isNew = product.getIsNew();
        product.setIsNew(!isNew);
        productRepository.save(product);
        log.info("In setProductIsNew - product with id : {} was set isNew to : {}", id, !isNew);
        return (isNew) ? "Product change is new to false Successfully!" : "Product change is new to true Successfully!";
    }

    @Override
    public Boolean deleteProduct(Long id) throws EntityNotFoundException {
        Product product = productRepository.findProductById(id);
        if (product == null) {
            log.warn("In deleteProduct - product with id : {} was not found", id);
            throw new EntityNotFoundException(Product.class, "product id", id.toString());
        }
        log.info("In deleteProduct - product with id : {} was found", id);
        productRepository.delete(product);
        log.info("In deleteProduct - product with id : {} was deleted", id);
        return true;
    }

}
