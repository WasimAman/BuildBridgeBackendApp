package com.wasim.buildbridge.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.wasim.buildbridge.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :usernameOrEmail OR u.email = :usernameOrEmail")
    Optional<User> findByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);

    @Query("SELECT u FROM User u " +
            "WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<User> search(@Param("query") String query);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
