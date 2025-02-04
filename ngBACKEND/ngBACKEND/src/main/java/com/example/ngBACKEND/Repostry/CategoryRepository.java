package com.example.ngBACKEND.Repostry;

import com.example.ngBACKEND.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findById(int id);
}
