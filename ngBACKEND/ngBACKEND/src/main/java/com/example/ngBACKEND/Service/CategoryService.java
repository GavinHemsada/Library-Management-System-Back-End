package com.example.ngBACKEND.Service;

import com.example.ngBACKEND.DTO.CategoryDTO;
import com.example.ngBACKEND.Entity.Category;
import com.example.ngBACKEND.Repostry.CategoryRepository;
import com.example.ngBACKEND.Response.CRUDRespons;
import com.example.ngBACKEND.Response.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CRUDRespons createCategory(CategoryDTO categoryDTO){
        Category category = new Category(categoryDTO.getNAME());
        categoryRepository.save(category);
        return new CRUDRespons("successfully added",true);
    }
    public Category readCategory(int id){
        Category category = categoryRepository.findById(id);
        if(category == null){
            throw new NotFoundException("cant find id");
        }
        return category;
    }
    public List<Category> readAllCategory(){
        return categoryRepository.findAll();
    }
    public CRUDRespons editCategory(CategoryDTO categoryDTO){
        Category category = categoryRepository.findById(categoryDTO.getID());
        if(category != null){
            category.setName(categoryDTO.getNAME());
            categoryRepository.save(category);
            return new CRUDRespons("successfully updated",true);
        }
        return new CRUDRespons("cant find id",false);
    }
    public CRUDRespons deleteCategory(int id){
        Category category = categoryRepository.findById(id);
        if(category != null){
            categoryRepository.delete(category);
            return  new CRUDRespons("successfully deleted",true);
        }
        return new CRUDRespons("cant find id",false);
    }
}
