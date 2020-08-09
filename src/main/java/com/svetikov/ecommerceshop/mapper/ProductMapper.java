package com.svetikov.ecommerceshop.mapper;

import com.svetikov.ecommerceshop.dto.model.ProductDto;
import com.svetikov.ecommerceshop.model.product.Product;
import com.svetikov.ecommerceshop.service.product.impl.CategoryService;
import com.svetikov.ecommerceshop.service.product.impl.ProductBrandService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
public class ProductMapper{

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductBrandService productBrandService;

    @Autowired
    private CategoryService categoryService;

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Product.class, ProductDto.class)
                .addMappings(m -> m.skip(ProductDto::setBrandId))
                .addMappings(m -> m.skip(ProductDto::setCategoryId))
                .setPostConverter(toDtoConverter());

        mapper.createTypeMap(ProductDto.class, Product.class)
                .addMappings(m -> m.skip(Product::setBrand))
                .addMappings(m -> m.skip(Product::setCategory))
                .setPostConverter(toEntityConverter());
    }

    public Product toEntity(ProductDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, Product.class);
    }

    public ProductDto toDto(Product entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, ProductDto.class);
    }

    public void updateEntity(ProductDto dto, Product entity) {
        if (dto != null && entity != null) {
            mapper.map(dto, entity);
        }
    }


    Converter<Product, ProductDto> toDtoConverter() {
        return context -> {
            Product source = context.getSource();
            ProductDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    Converter<ProductDto, Product> toEntityConverter() {
        return context -> {
            ProductDto source = context.getSource();
            Product destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    void mapSpecificFields(Product source, ProductDto destination) {
        destination.setBrandId(Objects.isNull(source) || Objects.isNull(source.getBrand().getId()) ? null : source.getBrand().getId());
        destination.setCategoryId(Objects.isNull(source) || Objects.isNull(source.getCategory().getId()) ? null : source.getCategory().getId());
    }

    void mapSpecificFields(ProductDto source, Product destination) {
        if (source.getBrandId() != null) {
            destination.setBrand(productBrandService.findById(source.getBrandId()));
            destination.setCategory(categoryService.findById(source.getCategoryId()));
        }

    }

}