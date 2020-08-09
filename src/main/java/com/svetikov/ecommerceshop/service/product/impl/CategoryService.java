package com.svetikov.ecommerceshop.service.product.impl;

import com.svetikov.ecommerceshop.exception.EntityNotFoundException;
import com.svetikov.ecommerceshop.model.product.Category;
import com.svetikov.ecommerceshop.repository.product.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService  {

    @Autowired
    private CategoryRepository categoryRepository;

    private final Logger log = LoggerFactory.getLogger(CategoryService.class);

    public List<Category> findAllCategories(){
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            log.warn("In findAllCategories - no categories was found");
            throw new EntityNotFoundException(Category.class, "all", "find all");
        }
        log.info("In findAllCategories - {} categories was found ", categories.size());
        return categories;
    }

    public Category save(Category category) {
        Category newCategory = categoryRepository.save(category);
        log.info("In save - category with id : {} was saved", newCategory.getId());
        return newCategory;
    }

    public Category findById(Long id) {
        Category category = categoryRepository.findCategoryById(id);
        if (category == null) {
            log.warn("In findById - no category with id : {} was found", id);
            throw new EntityNotFoundException(Category.class, "id", id.toString());
        }
        return category;
    }


}
