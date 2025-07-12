package com.example.ngBACKEND.Repostry;
import com.example.ngBACKEND.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    List<User> findByUserType(User.UserType userType);

    List<User> findByIsActive(Boolean isActive);

    @Query("SELECT u FROM User u WHERE u.name LIKE %:name%")
    List<User> findByNameContaining(@Param("name") String name);

    @Query("SELECT u FROM User u WHERE u.userType = :userType AND u.isActive = true")
    List<User> findActiveUsersByType(@Param("userType") User.UserType userType);

    boolean existsByEmail(String email);

    // Custom query for users with overdue books
    @Query("SELECT DISTINCT u FROM User u JOIN u.transactions t WHERE t.status = 'OVERDUE'")
    List<User> findUsersWithOverdueBooks();
}

