package com.example.ngBACKEND.Controller;


import com.example.ngBACKEND.DTO.CategoryDTO;
import com.example.ngBACKEND.Respons.Respons;
import com.example.ngBACKEND.Service.CategoryService;
import com.example.ngBACKEND.Util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Respons<?> getAllCategories() {
        try{
            return  new Respons<>(true,"getAllCategories",categoryService.getAllCategories());
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    //  Get category by ID
    @GetMapping("/{id}")
    public Respons<?> getCategoryById(@PathVariable Integer id) {
        try{
            return  new Respons<>(true,"getCategoryById",categoryService.getCategoryById(id));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    //  Get category by name
    @GetMapping("/name/{name}")
    public Respons<?> getCategoryByName(@PathVariable String name) {
        try{
            return  new Respons<>(true,"getCategoryByName",categoryService.getCategoryByName(name));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    //  Search categories by partial name
    @GetMapping("/search")
    public Respons<?> searchCategories(@RequestParam String name) {
        try{
            return  new Respons<>(true,"searchCategories",ResponseEntity.ok(categoryService.searchCategoriesByName(name)));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    //  Create a new category
    @PostMapping
    public Respons<?> createCategory(@RequestBody CategoryDTO categoryDTO) {
        try{
            Map<String, String> errors = ValidationUtil.validateObject(categoryDTO);
            if (!errors.isEmpty()) {
                return new Respons<>( false, "Input Validation failed", errors);
            }
            return  new Respons<>(true,"createCategory",categoryService.createCategory(categoryDTO));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    //  Update category
    @PutMapping("/{id}")
    public Respons<?> updateCategory(
            @PathVariable Integer id,
            @RequestBody CategoryDTO categoryDTO) {
        try{
            Map<String, String> errors = ValidationUtil.validateObject(categoryDTO);
            if (!errors.isEmpty()) {
                return new Respons<>( false, "Input Validation failed", errors);
            }
            return  new Respons<>(true,"updateCategory",categoryService.updateCategory(id, categoryDTO));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    //  Delete category
    @DeleteMapping("/{id}")
    public Respons<?> deleteCategory(@PathVariable Integer id) {
        try{
            return  new Respons<>(true,"deleteCategory",categoryService.deleteCategory(id));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }

    //  Check if category exists by name
    @GetMapping("/exists")
    public Respons<?> checkCategoryExists(@RequestParam String name) {
        try{
            return  new Respons<>(true,"checkCategoryExists",categoryService.categoryExists(name));
        }catch (RuntimeException e){
            return  new Respons<>(false,"runtime error", e.getMessage());
        }
    }
}