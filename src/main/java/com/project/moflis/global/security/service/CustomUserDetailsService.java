package com.project.moflis.global.security.service;

import com.project.moflis.global.security.model.CustomUserDetails;
import com.project.moflis.user.entity.User;
import com.project.moflis.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new IllegalArgumentException("유저가 존재하지 않습니다.");
        }
        return new CustomUserDetails(user);
    }
}
