package com.svetikov.ecommerceshop.controller;

import com.svetikov.ecommerceshop.model.product.Category;
import com.svetikov.ecommerceshop.service.product.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class PageNavigationController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = {"/", "home", "index"})
    public String home(Model model) {
        List<Category> list = categoryService.findAllCategories();
        model.addAttribute("categories", list);
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(
            @RequestParam(name = "error", required = false) Optional<String> error,
            Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        error.ifPresent(c -> model.addAttribute("message","Username or password is invalid!"));
        return "login";
    }

    @RequestMapping(value = "/contact")
    public String contact(Model model) {
        return "contact";
    }

    @RequestMapping(value = "/access-denied", method = RequestMethod.GET)
    public String accessDenied() {
        return "accessDenied";
    }


}
