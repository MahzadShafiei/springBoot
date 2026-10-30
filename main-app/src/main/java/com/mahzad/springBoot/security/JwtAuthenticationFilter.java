
package com.mahzad.springBoot.security;

import com.mahzad.springBoot.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private  final UserRepository userRepository;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService,
            UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            if (!jwtService.isTokenValid(token)) {
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Invalid token"
                );
                return;
            }

            String username = jwtService.extractUsername(token);
            String tokenType = jwtService.extractTokenType(token);
            long tokenVersion = jwtService.extractTokenVersion(token);

            if (!tokenType.equals("ACCESS") &&
                    !tokenType.equals("PASSWORD_CHANGE")) {
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Unknown token type"
                );
                return;
            }

            var user = userRepository.getByUsernameByJpql(username)
                    .orElse(null);

            if (user == null ||
                    tokenVersion != user.getTokenVersion()) {
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Token is no longer valid"
                );
                return;
            }

            if ("PASSWORD_CHANGE".equals(tokenType)) {
                boolean allowedEndpoint =
                        "POST".equalsIgnoreCase(request.getMethod())
                                && "/auth/change-password".equals(
                                request.getServletPath()
                        );

                if (!allowedEndpoint) {
                    response.sendError(
                            HttpServletResponse.SC_FORBIDDEN,
                            "Password change required"
                    );
                    return;
                }
            }

            if (SecurityContextHolder.getContext()
                        .getAuthentication() == null) {

                    UserDetails userDetails =
                            userDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                }

            filterChain.doFilter(request, response);

        } catch (io.jsonwebtoken.JwtException |
                 IllegalArgumentException ex) {
            SecurityContextHolder.clearContext();

            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid token"
            );
        }

        filterChain.doFilter(request, response);
    }
}
