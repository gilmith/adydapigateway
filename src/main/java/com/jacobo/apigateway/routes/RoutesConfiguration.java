package com.jacobo.apigateway.routes;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RoutesConfiguration {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(route -> route
                        .path("/api/bbdd/**")
                        .filters(f -> f.rewritePath("/api/bbdd/(?<remaining>.*)", "/bbdd/${remaining}"))
                        .uri("https://localhost:10004"))
                .build();
    }
}
