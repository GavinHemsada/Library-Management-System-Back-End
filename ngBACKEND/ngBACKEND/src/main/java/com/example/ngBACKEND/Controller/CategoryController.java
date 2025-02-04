package com.example.ngBACKEND.Controller;

import com.example.ngBACKEND.DTO.CategoryDTO;
import com.example.ngBACKEND.Entity.Category;
import com.example.ngBACKEND.Response.CRUDRespons;
import com.example.ngBACKEND.Service.CategoryService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/ngCRUD/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CategoryDTO categoryDTO){
        CRUDRespons create = categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok(create);
    }
    @GetMapping("/read")
    public Category read(@RequestParam int id){
        return categoryService.readCategory(id);
    }
    @GetMapping("/readAll")
    public List<Category> readAll(){
        return categoryService.readAllCategory();
    }
    @PutMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody CategoryDTO categoryDTO){
        CRUDRespons edit = categoryService.editCategory(categoryDTO);
        return ResponseEntity.ok(edit);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody JsonNode jsonNode){
        int id = jsonNode.get("id").asInt();
        CRUDRespons delete = categoryService.deleteCategory(id);
        return ResponseEntity.ok(delete);
    }
}
