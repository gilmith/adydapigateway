package com.jacobo.apigateway.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;


import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Component
@Configuration
@Slf4j
public class RouterLogin {
	
	@Value("${uri-redirect}")
	private String uriRedirect;

	
	
	
	@Bean("getLogin")
	public RouteLocator getLogin(RouteLocatorBuilder rlb) {
		return rlb.routes()
				.route("create_service",path -> path.path("/adyd/oauth/login/create")
						.filters(f ->
							f.filter(createFilter())
							.rewritePath("/adyd/oauth/login/create", "/login/create"))									
						.uri(uriRedirect)).build();												
	}

	private GatewayFilter createFilter() {
		return (exchange, chain) -> {			
			log.info("en el filter de la ruta");
		    val method = exchange.getRequest().getMethod();
		    if(method.equals(HttpMethod.GET)) {
		    	log.info("Ha recibido un GET");
		    } else {
		    	log.info("Ha recibido un {} con el path create ", method.name());
		    }
		    return chain.filter(exchange);
		};
	}
	


}
