package com.svetikov.ecommerceshop.controller;

import com.svetikov.ecommerceshop.dto.model.ProductDto;
import com.svetikov.ecommerceshop.model.product.Category;
import com.svetikov.ecommerceshop.model.product.ProductBrand;
import com.svetikov.ecommerceshop.service.product.impl.CategoryService;
import com.svetikov.ecommerceshop.service.product.impl.ProductBrandService;
import com.svetikov.ecommerceshop.service.product.impl.ProductServiceImpl;
import com.svetikov.ecommerceshop.util.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/manage")
public class ManagementController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductBrandService productBrandService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String contact(
            Model model,
            @RequestParam(name = "success") Optional<String> message) {
        ProductDto product = new ProductDto();
        Category category = new Category();
        List<Category> categories = categoryService.findAllCategories();
        List<ProductBrand> productBrands = productBrandService.findAllProductBrands();

        if (!model.containsAttribute("product")) {
            model.addAttribute("product", product);
        }
        model.addAttribute("categories", categories);
        model.addAttribute("brands", productBrands);
        model.addAttribute("category", category);
        model.addAttribute("message", message.orElse(""));
        model.addAttribute("operation", "create");

        return "manageProducts";
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public String getEditableProduct(@PathVariable(name = "id") Long id, Model model) {
        ProductDto product = productService.findSingleProductDto(id);
        Category category = new Category();
        List<Category> categories = categoryService.findAllCategories();
        List<ProductBrand> productBrands = productBrandService.findAllProductBrands();
        model.addAttribute("product", product);
        model.addAttribute("category", category);
        model.addAttribute("brands", productBrands);
        model.addAttribute("categories", categories);
        model.addAttribute("operation", "update");
        return "manageProducts";
    }

    @RequestMapping(value = "/product/add", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String manageCreateProduct(
            @Validated(ProductDto.Create.class) @ModelAttribute("product") ProductDto product,
            BindingResult bindingResults,
            RedirectAttributes attr,
            @RequestParam("photo") MultipartFile photoUpload) {

        if (bindingResults.hasErrors()) {
            attr.getFlashAttributes().clear();
            attr.addFlashAttribute("org.springframework.validation.BindingResult.product", bindingResults);
            attr.addFlashAttribute("product", product);
            return "redirect:/manage?success=invalid";
        }

        productService.saveProductDto(product, photoUpload);
        return "redirect:/manage" + "?success=product";
    }

    @RequestMapping(value = "/product/update", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String manageUpdateProduct(
            @Validated(ProductDto.Update.class) @ModelAttribute("product") ProductDto product,
            BindingResult bindingResult,
            @RequestParam("photo") MultipartFile photoUpload,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "invalid");
            return "manageProducts";
        }
        productService.updateProductDto(product, photoUpload);
        return "redirect:/manage" + "?success=product";

    }

    @RequestMapping(value = "/product/{id}/activation", method = RequestMethod.GET)
    @ResponseBody
    public String managePostProductActivation(@PathVariable(name = "id") Long id)  {
        String message = productService.setProductActivation(id);
        return message;
    }

    @RequestMapping(value = "/product/{id}/new", method = RequestMethod.GET)
    @ResponseBody
    public String managePostProductIsNew(@PathVariable(name = "id") Long id) {
        String message = productService.setProductIsNew(id);
        return message;
    }

    @RequestMapping(value = "/product/{id}/delete", method = RequestMethod.GET)
    public String manageDeleteProduct(@PathVariable(name = "id") Long id) {
        Boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {
            return "redirect:/manage" + "?success=delete";
        }
        return "redirect:/manage" + "?success=invalid";
    }

    @RequestMapping(value = "/product/category", method = RequestMethod.POST)
    public String managePostCategory(
            HttpServletRequest request,
            @ModelAttribute(name = "category") Category category,
            @RequestParam("photo") MultipartFile photoUpload) {

        if (category != null) {
            String imageUrl = FileUploadService.uploadFile(photoUpload);
            category.setImageCode(imageUrl);
            categoryService.save(category);
        }

        return "redirect:/manage" + "?success=category";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String handleError(HttpServletRequest req, ConstraintViolationException ex, Model model) {
        model.addAttribute("exception", ex.getMessage());
        return "manageProducts";
    }

}
