package com.mahzad.springBoot.security;

import com.mahzad.springBoot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PasswordPolicyService {

    private static final long PASSWORD_MAX_AGE_DAYS = 90;
    private final UserRepository _userRepository;
    public PasswordPolicyService(UserRepository userRepository)
    {
        _userRepository = userRepository;
    }

    public boolean isPasswordExpired(String username)
    {
        var user = _userRepository.getByUsernameByJpql(username)
                .orElseThrow(() ->
                        new IllegalArgumentException("Username not found."));

        if(user.getPasswordChangedAt() == null)
            return true;

        return user.getPasswordChangedAt()
                .plusDays(PASSWORD_MAX_AGE_DAYS)
                .isBefore(LocalDateTime.now());
    }
}
