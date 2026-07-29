package com.tekhoufeha.auth.security;

import com.tekhoufeha.auth.entity.AuthUser;
import com.tekhoufeha.auth.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        AuthUser authUser = authUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found."));

        return User.builder()
                .username(authUser.getEmail())
                .password(
                        authUser.getPassword() != null
                                ? authUser.getPassword()
                                : ""
                )
                .authorities(authUser.getRole().name())
                .build();
    }
}