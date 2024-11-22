package com.sparta.oceanbackend.domain.hotkeyword.entity;

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
@Table(name = "hotkeywords")
@Getter
@NoArgsConstructor
public class HotKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 10)
    private String keyword;

    @Builder
    public HotKeyword(
        String keyword
        ) {
        this.keyword = keyword;
    }
}
