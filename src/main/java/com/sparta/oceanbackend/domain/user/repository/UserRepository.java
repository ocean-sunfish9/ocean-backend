package com.sparta.oceanbackend.domain.user.repository;

import com.sparta.oceanbackend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, QueryDslUserRepository {
    Optional<User> findByName(String name);
}
