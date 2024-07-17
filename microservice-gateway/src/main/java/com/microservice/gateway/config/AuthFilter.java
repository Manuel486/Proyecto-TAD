package com.microservice.gateway.config;

import com.microservice.gateway.dto.TokenDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private WebClient.Builder webClient;
    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    public AuthFilter(WebClient.Builder webClient) {
        super(Config.class);
        this.webClient = webClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                logger.warn("Authorization header is missing");
                return onError(exchange, HttpStatus.BAD_REQUEST);
            }
            String tokenHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String[] chunks = tokenHeader.split(" ");
            if (chunks.length != 2 || !chunks[0].equals("Bearer")) {
                logger.warn("Authorization header is invalid");
                return onError(exchange, HttpStatus.BAD_REQUEST);
            }
            return webClient.build()
                    .post()
                    .uri("http://msvc-auth/api/auth/validate?token=" + chunks[1])
                    .retrieve()
                    .bodyToMono(TokenDto.class)
                    .map(t -> {
                        logger.info("Token is valid: {}", t.getToken());
                        return exchange;
                    })
                    .flatMap(chain::filter)
                    .onErrorResume(e -> {
                        logger.error("Error validating token", e);
                        return onError(exchange, HttpStatus.UNAUTHORIZED);
                    });
        };
    }

    public Mono<Void> onError(ServerWebExchange exchange, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }

    public static class Config {
    }
}