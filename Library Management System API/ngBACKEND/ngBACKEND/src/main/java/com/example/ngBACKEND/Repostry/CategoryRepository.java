package com.example.ngBACKEND.Repostry;

import com.example.ngBACKEND.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByCategoryName(String categoryName);

    @Query("SELECT c FROM Category c WHERE c.categoryName LIKE %:name%")
    List<Category> findByNameContaining(@Param("name") String name);

    boolean existsByCategoryName(String categoryName);
}
