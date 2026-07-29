package com.tekhoufeha.apigateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter {


    private final JwtService jwtService;


    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {


        String path =
                exchange.getRequest()
                        .getURI()
                        .getPath();


        // Routes publiques
        if (path.startsWith("/api/auth/login")
                || path.startsWith("/api/auth/register")
                || path.startsWith("/oauth2")) {

            return chain.filter(exchange);
        }



        String authHeader =
                exchange.getRequest()
                        .getHeaders()
                        .getFirst(
                                HttpHeaders.AUTHORIZATION
                        );


        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {

            return unauthorized(exchange);
        }


        String token =
                authHeader.substring(7);



        if (!jwtService.isTokenValid(token)) {

            return unauthorized(exchange);
        }



        return chain.filter(exchange);
    }




    private Mono<Void> unauthorized(
            ServerWebExchange exchange) {


        exchange.getResponse()
                .setStatusCode(
                        HttpStatus.UNAUTHORIZED
                );


        return exchange.getResponse()
                .setComplete();
    }

}