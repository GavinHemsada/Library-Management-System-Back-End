package com.example.ngBACKEND.Repostry;

import com.example.ngBACKEND.Entity.Books;
import com.example.ngBACKEND.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface BooksRepository extends JpaRepository<Books, Integer> {

    List<Books> findByCategory(Category category);

    List<Books> findByCategoryCategoryId(Integer categoryId);

    @Query("SELECT b FROM Books b WHERE b.title LIKE %:title%")
    List<Books> findByTitleContaining(@Param("title") String title);

    @Query("SELECT b FROM Books b WHERE b.author LIKE %:author%")
    List<Books> findByAuthorContaining(@Param("author") String author);

    Optional<Books> findByIsbn(String isbn);

    @Query("SELECT b FROM Books b WHERE b.copiesAvailable > 0 AND b.isActive = true")
    List<Books> findAvailableBooks();

    @Query("SELECT b FROM Books b WHERE b.copiesAvailable = 0 AND b.isActive = true")
    List<Books> findOutOfStockBooks();

    @Query("SELECT b FROM Books b WHERE b.publicationYear BETWEEN :startYear AND :endYear")
    List<Books> findByPublicationYearBetween(@Param("startYear") Integer startYear, @Param("endYear") Integer endYear);

    @Query("SELECT b FROM Books b WHERE b.title LIKE %:keyword% OR b.author LIKE %:keyword%")
    List<Books> searchBooks(@Param("keyword") String keyword);

    boolean existsByIsbn(String isbn);
}
