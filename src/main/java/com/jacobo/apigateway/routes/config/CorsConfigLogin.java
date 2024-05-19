package com.jacobo.apigateway.routes.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class CorsConfigLogin extends CorsConfiguration {

	private final List<String> allowedHeaders = List.of("Origin", "Access-Control-ALLOW-ORIGIN", "Content-Type",
			"ACCEPT", "JWT-TOKEN", "AUTHORIZATION", "ORIGIN, ACCEPT", "X-REQUESTED-WITH",
			"ACCESS-CONTROL-REQUEST-METHOD", "ACCESS-CONTROL-REQUEST-HEADERS");

	private final List<String> exposedHeaders = List.of("Origin", "Content-Type", "Accept", "Jwt-Token",
			"Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Origin",
			"Access-Control-Allow-Credentials", "File-Name");

	@Bean
	public CorsWebFilter corsWebFilter() {
		log.info("cargando los cors de login");
		final CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(Collections.singletonList("*"));
		corsConfig.setMaxAge(3600L);
		corsConfig.setAllowedMethods(Arrays.asList("POST"));
		corsConfig.setAllowedHeaders(allowedHeaders);
		val source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/adyd/oauth/**", corsConfig);
		return new CorsWebFilter(source);
	}

}
