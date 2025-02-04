package com.example.ngBACKEND.Repostry;

import com.example.ngBACKEND.Entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface BooksRepository extends JpaRepository<Books, Integer> {
    Books findByid(int id);
}
