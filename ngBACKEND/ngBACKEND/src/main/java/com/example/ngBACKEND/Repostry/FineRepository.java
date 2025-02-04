package com.example.ngBACKEND.Repostry;

import com.example.ngBACKEND.Entity.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface FineRepository extends JpaRepository<Fine, Integer> {
    Fine findById(int id);
}
