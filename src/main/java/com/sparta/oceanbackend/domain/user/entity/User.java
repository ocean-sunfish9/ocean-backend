package com.sparta.oceanbackend.domain.user.entity;

import com.sparta.oceanbackend.common.entity.Timestamped;
import com.sparta.oceanbackend.common.exception.ExceptionType;
import com.sparta.oceanbackend.common.exception.ResponseException;
import com.sparta.oceanbackend.domain.user.repository.UserRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 20)
    String name;
    @Column(nullable = false)
    String password;

    @Builder
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void checkNameDuplicate(String name, UserRepository userRepository) {
        if (userRepository.findByName(name).isPresent()) {
            throw new ResponseException(ExceptionType.NAME_IN_USE);
        }
    }
}
