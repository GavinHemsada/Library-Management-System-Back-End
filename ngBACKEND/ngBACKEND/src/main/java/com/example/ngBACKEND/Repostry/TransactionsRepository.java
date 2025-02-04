package com.example.ngBACKEND.Repostry;

import com.example.ngBACKEND.Entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {
    Transactions findById(int id);
}
