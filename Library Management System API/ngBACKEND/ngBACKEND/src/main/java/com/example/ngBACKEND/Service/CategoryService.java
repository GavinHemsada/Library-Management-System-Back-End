package com.example.ngBACKEND.Service;

import com.example.ngBACKEND.DTO.CategoryDTO;
import com.example.ngBACKEND.Entity.Category;
import com.example.ngBACKEND.Repostry.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Cacheable(value = "allCategory")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Integer id) {
        return categoryRepository.findById(id);
    }

    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByCategoryName(name);
    }

    public List<Category> searchCategoriesByName(String name) {
        return categoryRepository.findByNameContaining(name);
    }

    public Category createCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByCategoryName(categoryDTO.getCategoryName())) {
            throw new RuntimeException("Category with name '" + categoryDTO.getCategoryName() + "' already exists");
        }
        Category category = convertToEntity(categoryDTO);
        return categoryRepository.save(category);
    }
    @CacheEvict(value = {"allCategory"}, allEntries = true)
    public Category updateCategory(Integer id, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        if (!existingCategory.getCategoryName().equals(categoryDTO.getCategoryName()) &&
                categoryRepository.existsByCategoryName(categoryDTO.getCategoryName())) {
            throw new RuntimeException("Category with name '" + categoryDTO.getCategoryName() + "' already exists");
        }

        existingCategory.setCategoryName(categoryDTO.getCategoryName());
        existingCategory.setDescription(categoryDTO.getDescription());
        return categoryRepository.save(existingCategory);
    }
    @CacheEvict(value = {"allCategory"}, allEntries = true)
    public String deleteCategory(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
        return "Successful Delete";
    }

    public boolean categoryExists(String categoryName) {
        return categoryRepository.existsByCategoryName(categoryName);
    }

    private Category convertToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setDescription(categoryDTO.getDescription());
        category.setCreatedAt(LocalDateTime.now());
        return category;
    }
}
