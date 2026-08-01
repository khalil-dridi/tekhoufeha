package com.tekhoufeha.apigateway.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tekhoufeha.apigateway.dto.ErrorResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Component;

import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;


import java.time.LocalDateTime;



@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter {



    private final JwtService jwtService;

    private final ObjectMapper objectMapper;




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




        String userId =
                jwtService.extractUserId(token);




        ServerWebExchange modifiedExchange =
                exchange.mutate()
                        .request(request ->
                                request.headers(headers ->
                                        headers.add(
                                                "X-User-Id",
                                                userId
                                        )
                                )
                        )
                        .build();



        return chain.filter(modifiedExchange);
    }







    private Mono<Void> unauthorized(
            ServerWebExchange exchange) {


        ErrorResponse error =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.UNAUTHORIZED.value(),
                        "Invalid or expired JWT token"
                );



        exchange.getResponse()
                .setStatusCode(
                        HttpStatus.UNAUTHORIZED
                );


        exchange.getResponse()
                .getHeaders()
                .add(
                        HttpHeaders.CONTENT_TYPE,
                        "application/json"
                );



        try {


            byte[] bytes =
                    objectMapper.writeValueAsBytes(error);



            return exchange.getResponse()
                    .writeWith(
                            Mono.just(
                                    exchange.getResponse()
                                            .bufferFactory()
                                            .wrap(bytes)
                            )
                    );


        } catch (Exception exception) {


            return exchange.getResponse()
                    .setComplete();
        }
    }

}