package com.mahzad.springBoot.security;

import com.mahzad.springBoot.dto.ChangePasswordRequest;
import com.mahzad.springBoot.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordPolicyService passwordPolicyService;
    private final AuthService authService;

    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager, PasswordPolicyService passwordPolicyService, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordPolicyService = passwordPolicyService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        var tokenType = passwordPolicyService.isPasswordExpired(loginRequest.getUsername())
                ? "PASSWORD_CHANGE": "ACCESS";

        var  token = jwtService.generateToken(authentication.getName(), tokenType);

        return new LoginResponse(token);
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePasswordRequest request,
            Authentication authentication) {

        authService.changePassword(
                authentication.getName(),
                request.getCurrentPassword(),
                request.getNewPassword()
        );

        return ResponseEntity.ok("Password changed successfully. Please login again.");
    }
}
