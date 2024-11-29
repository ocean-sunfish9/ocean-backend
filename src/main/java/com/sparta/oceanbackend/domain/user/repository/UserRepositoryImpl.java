package com.sparta.oceanbackend.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.oceanbackend.domain.user.entity.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements QueryDslUserRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public void updatePassword(Long id, String password) {
        QUser user = QUser.user;

        queryFactory.update(user)
                .set(user.password, password)
                .where(user.id.eq(id))
                .execute();
    }
}
