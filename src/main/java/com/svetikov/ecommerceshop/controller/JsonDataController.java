package com.svetikov.ecommerceshop.controller;

import com.svetikov.ecommerceshop.model.product.Product;
import com.svetikov.ecommerceshop.service.product.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class JsonDataController {

    @Autowired
    private ProductServiceImpl productService;

    @RequestMapping(value = "/json/data/all/products", method = RequestMethod.GET)
    @ResponseBody
    public List<Product> getPoducts() {
        return productService.findAll();
    }

    @RequestMapping(value = "/json/data/products/maxPrice", method = RequestMethod.GET)
    @ResponseBody
    public Integer getMaxPrice() {
        return productService.getMaxPrice();
    }

}
