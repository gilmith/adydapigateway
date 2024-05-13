package com.jacobo.apigateway.filters;

import java.net.URI;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.web.server.ServerWebExchange;

import lombok.val;
import reactor.core.publisher.Mono;

public class FilterLogin extends AbstractGatewayFilterFactory<Object> {

	@Override
	public GatewayFilter apply(Object config) {
		return (exchange, chain) -> {
            val method = exchange.getRequest().getMethod();

//            if ("GET".equals(method)) {
//                return chain.filter(exchange);
//            } else if ("POST".equals(method)) {
//                // Realiza la redirección solo para peticiones POST
//                return redirectToService(exchange);
//            }

            return chain.filter(exchange);
        };
	}
	
//    private Mono<Void> redirectToService(ServerWebExchange exchange) {
//        exchange.getResponse().setStatusCode(HttpStatus.SEE_OTHER);
//        exchange.getResponse().getHeaders().setLocation(URI.create("http://servicio-login"));
//        return exchange.getResponse().setComplete();
//    }

}
