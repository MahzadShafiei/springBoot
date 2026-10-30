package com.mahzad.springBoot.service;

import com.mahzad.springBoot.model.User;
import com.mahzad.springBoot.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void changePassword(String username,
                               String currentPassword,
                               String newPassword) {

        User user = userRepository.getByUsernameByJpql(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // بررسی رمز فعلی
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new BadCredentialsException("Current password is incorrect");
        }

        // ذخیره رمز جدید
        user.setPassword(passwordEncoder.encode(newPassword));

        // ثبت زمان تغییر رمز
        user.setPasswordChangedAt(LocalDateTime.now());

        // باطل کردن تمام توکن‌های قبلی
        user.setTokenVersion(user.getTokenVersion() + 1);

        userRepository.save(user);
    }
}