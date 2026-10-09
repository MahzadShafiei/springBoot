package com.mahzad.springBoot.security;

import com.mahzad.springBoot.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
    {
        com.mahzad.springBoot.model.User user = userRepository.getByUsernameByJpql(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        var roles = user.getUserRoles().stream().map(c-> c.getRole().getTitle()).toList();

        return User.builder()
                .username(user.getName())
                .password(user.getPassword())
                .roles(roles.toArray(new String[0]))
                .build();
    }
}
