package com.sparta.oceanbackend.api.user.service;

import com.sparta.oceanbackend.domain.user.entity.User;
import com.sparta.oceanbackend.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.userdetails.User.builder;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) {
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("가입되지 않은 회원입니다."));
        return builder()
                .username(user.getName())
                .password(user.getPassword())
                .build();
    }
}
