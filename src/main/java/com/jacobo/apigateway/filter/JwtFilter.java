package com.jacobo.apigateway.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter implements GlobalFilter { // Implementa GlobalFilter

    @Value("${secret}")
    private String secret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("JWT filter applied globally");
        final ServerHttpRequest request = exchange.getRequest();
        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            return onError(exchange, "JWT not found", HttpStatus.UNAUTHORIZED);
        }
        final String authorizationHeader = request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).getFirst();
        if (!authorizationHeader.startsWith("Bearer ")) {
            return onError(exchange, "Invalid Authorization header format", HttpStatus.UNAUTHORIZED);
        }

        final String token = authorizationHeader.substring(7); // Remove "Bearer " prefix

        try {
            val jwt = validateToken(token);
            return chain.filter(exchange.mutate().request(request).build());

        } catch (AlgorithmMismatchException e) {
            log.error("Wrong algorithm. The algorithm used does not match the corresponding one in the token header");
            return onError(exchange, "wrong algorithm", HttpStatus.UNAUTHORIZED);
        } catch (SignatureVerificationException e) {
            log.error("Invalid signature. The value of secret is incorrect");
            return onError(exchange, "Invalid signature", HttpStatus.UNAUTHORIZED);
        } catch (InvalidClaimException e) {
            log.error("Wrong audience. Required audiences are missing");
            return onError(exchange, "Invalid claims", HttpStatus.UNAUTHORIZED);
        } catch (JWTDecodeException e) {
            if (e.getMessage().contains("token was expected")) {
                log.error("JWT decoding failed. The token does not contain the necessary three distinct parts.");
                return onError(exchange, "Invalid towken", HttpStatus.UNAUTHORIZED);

            } else {
                log.error("Invalid decoding. The token format is incorrect.");
                return onError(exchange, "Invalid decoding", HttpStatus.UNAUTHORIZED);

            }
        } catch (TokenExpiredException e) {
            log.error(e.getMessage());
            return onError(exchange, "token expired", HttpStatus.UNAUTHORIZED);
        }
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private DecodedJWT validateToken(String requestTokenHeader) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC384(secret)).build();
        return verifier.verify(requestTokenHeader);

    }

}
