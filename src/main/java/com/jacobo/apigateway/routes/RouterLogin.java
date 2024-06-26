package com.jacobo.apigateway.routes;

import java.util.function.Function;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RouterLogin {
	
	@Bean("getLogin")
	public RouteLocator getLogin(RouteLocatorBuilder rlb) {
		return rlb.routes()
				.route("create_service",path -> path.path("/adyd/oauth/login/create")
						.filters(f ->
							f.filter(extracted())
							.rewritePath("/adyd/oauth/login/create", "/login/create")
						)
						.uri("https://login:10002")
				).build();
	}

	private GatewayFilter extracted() {
		return (exchange, chain) -> {
			
			log.info("en el filter de la ruta");
		    val method = exchange.getRequest().getMethod();
		    if(method.equals(HttpMethod.GET)) {
		    	log.info("Ha recibido un GET");
		    } else {
		    	log.info("Ha recibido un {}", method.name());
		    }
		    return chain.filter(exchange);
		};
	}
	


}
