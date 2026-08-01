package com.tekhoufeha.auth.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;



@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtService jwtService;

    private final CustomUserDetailsService customUserDetailsService;



    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {



        String authHeader =
                request.getHeader("Authorization");



        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {


            filterChain.doFilter(request, response);
            return;
        }



        String jwt =
                authHeader.substring(7);



        try {


            String userId =
                    jwtService.extractUsername(jwt);



            if (userId != null
                    && SecurityContextHolder
                    .getContext()
                    .getAuthentication() == null) {



                UserDetails userDetails =
                        customUserDetailsService
                                .loadUserByUsername(userId);




                if (jwtService.isTokenValid(
                        jwt,
                        userDetails
                )) {



                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );



                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );



                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authenticationToken);
                }
            }



        } catch (Exception e) {


            SecurityContextHolder.clearContext();


            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED
            );


            response.setContentType(
                    "application/json"
            );


            response.getWriter().write("""
                    {
                        "status": 401,
                        "error": "Unauthorized",
                        "message": "Invalid or expired JWT token"
                    }
                    """);


            return;
        }



        filterChain.doFilter(request, response);
    }
}