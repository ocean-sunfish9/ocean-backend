package com.sparta.oceanbackend.domain.user.repository;

import com.sparta.oceanbackend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, QueryDslUserRepository {
    Optional<User> findByName(String name);

    @Modifying
    @Query("UPDATE User u SET u.name = :name, u.password = :password, u.deletedAt = CURRENT_TIMESTAMP WHERE u.id = :id")
    void deleteUserById(Long id, String name, String password);
}
