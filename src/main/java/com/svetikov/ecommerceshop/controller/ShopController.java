package com.svetikov.ecommerceshop.controller;

import com.svetikov.ecommerceshop.dto.model.ProductDto;
import com.svetikov.ecommerceshop.model.product.Category;
import com.svetikov.ecommerceshop.model.product.Product;
import com.svetikov.ecommerceshop.model.product.ProductImage;
import com.svetikov.ecommerceshop.service.product.impl.CategoryService;
import com.svetikov.ecommerceshop.service.product.impl.ProductServiceImpl;
import com.svetikov.ecommerceshop.util.PagePaginationUtil;
import com.svetikov.ecommerceshop.util.ProductListRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@Controller
public class ShopController {


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductServiceImpl productService;

    @RequestMapping(value = "/shop", method = RequestMethod.GET)
    public String getShop(
            Model model,
            ProductListRequest request,
            Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.fromString(
                Optional.ofNullable(request.getOrder()).orElse("desc")),
                Optional.ofNullable(request.getSort()).orElse("dateReceipt"));

        Page<Product> productPage = productService.findAllPageable(request, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort));
        List<Category> categories = categoryService.findAllCategories();
        Integer maxProductPrice = productService.findMaxProductPriceByCategory(request.getCategory());
        Integer minProductPrice = productService.findMinProductPriceByCategory(request.getCategory());
        int[] pagination = PagePaginationUtil.computePagination(productPage);
        model.addAttribute("categories", categories);
        model.addAttribute("productsPage", productPage);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("pagination", pagination);
        model.addAttribute("maxPrice", maxProductPrice);
        model.addAttribute("minPrice", minProductPrice);
        model.addAttribute("request", request);
        return "shop";
    }

    @RequestMapping(value = "/shop/product/{id}", method = RequestMethod.GET)
    public String getSingleProduct(Model model, @PathVariable(name = "id") Long productId) {

        ProductDto product = productService.findSingleProductViewDto(productId);
        List<ProductImage> productImages = productService.findAllProductImages(productId);
        model.addAttribute("product", product);
        model.addAttribute("productImages", productImages);

        return "product";
    }


}
