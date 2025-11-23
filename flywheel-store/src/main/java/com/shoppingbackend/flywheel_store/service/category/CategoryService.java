package com.shoppingbackend.flywheel_store.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shoppingbackend.flywheel_store.exceptions.AlreadyExistsException;
import com.shoppingbackend.flywheel_store.exceptions.CategoryNotFoundExcaption;
import com.shoppingbackend.flywheel_store.model.Category;
import com.shoppingbackend.flywheel_store.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundExcaption("Category Not found"));// or we can create a generic class for this called resource not found
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.ofNullable(category).filter(c -> !categoryRepository.existsByName(c.getName()))
            .map(categoryRepository :: save)
            .orElseThrow(() -> new AlreadyExistsException(category.getName() + " already exists"));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(oldcategory -> {
            oldcategory.setName(category.getName());
            return categoryRepository.save(oldcategory);
        }).orElseThrow(() -> new CategoryNotFoundExcaption("Category not found"));
    }

    @Override
    public void deleteCategoryById(Long id) {
       categoryRepository.findById(id).
            ifPresentOrElse(categoryRepository :: delete, () ->
                {throw new CategoryNotFoundExcaption("Category not Found");});
    }

}
