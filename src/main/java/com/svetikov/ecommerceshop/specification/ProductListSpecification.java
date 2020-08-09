package com.svetikov.ecommerceshop.specification;

import com.svetikov.ecommerceshop.model.product.Product;
import com.svetikov.ecommerceshop.util.ProductListRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class ProductListSpecification extends BaseSpecification<Product,ProductListRequest> {

    @Override
    public Specification<Product> getFilter(ProductListRequest request) {
        return (root, query, cb) -> {
            query.distinct(true); //Important because of the join in the addressAttribute specifications


            return where(
                    where(productNameContains(request.getSearch())))
                    .and(productBrandFilter(request.getBrand()))
                    .and(productCategoryIs(request.getCategory()))
                    .and(productPriceFilter(request.getPriceF(),request.getPriceT()))
                    .toPredicate(root, query, cb);
        };
    }


    private Specification<Product> productAttributeContains(String attribute, String value) {
        return (root, query, cb) -> {
            if(value == null) {
                return null;
            }

            return cb.like(
                    cb.lower(root.get(attribute)),
                    containsLowerCase(value)
            );
        };
    }

    private Specification<Product> productBrandContains(String attribute, String value) {
        return (root, query, cb) -> {
            if(value == null) {
                return null;
            }

            return cb.like(
                    cb.lower(root.get(attribute).get("name")),
                    containsLowerCase(value)
            );
        };
    }

    private Specification<Product> productWithinCategory(String attribute, String value) {
        return (root, query, cb) -> {
            if(value == null) {
                return null;
            }

            return cb.equal(
                    cb.lower(root.get(attribute).get("webUrl")),
                    value
            );
        };
    }

    private Specification<Product> productPriceFilterSpec(String attribute, Integer priceF, Integer priceT) {
        return (root, query, cb) -> {
            if(priceF == null || priceT ==null) {
                return null;
            }

            return cb.and(
                    cb.greaterThan(root.get(attribute),priceF ),
                    cb.lessThan(root.get(attribute),priceT)
            );
        };
    }

    private Specification<Product> productNameContains(String name) {
        return productAttributeContains("name", name);
    }

    private Specification<Product> productBrandFilter(String name) {
        return productBrandContains("brand", name);
    }

    private Specification<Product> productCategoryIs(String web_url) {
        return productWithinCategory("category", web_url);
    }

    private Specification<Product> productPriceFilter(Integer priceF,Integer priceT) {
        return productPriceFilterSpec("unitPrice", priceF,priceT);
    }



}
