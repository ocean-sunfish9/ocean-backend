package com.sparta.oceanbackend.domain.user.repository;

public interface QueryDslUserRepository {
    void updatePassword(Long id, String password);
}
