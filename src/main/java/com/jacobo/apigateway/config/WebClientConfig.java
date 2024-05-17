package com.jacobo.apigateway.config;

import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import lombok.val;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

	@Value("${trust.store:keystore/springboot.p12}")
	private ClassPathResource trustStore;

	@Value("${trust.store.password:password}")
	private String trustStorePassword;
//
//	@Bean
//	public WebClient metricsCustomizer() throws Exception {
////		SslContext sslContext = sslContext();
////		val cliente = HttpClient.create().secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));
////		ClientHttpConnector connector = new ReactorClientHttpConnector(cliente);
////		return WebClient.builder().clientConnector(connector).build();
//
//	}
//
//	private SslContext sslContext() throws Exception {
//		KeyStore keyStore = KeyStore.getInstance("PKCS12");
//		try (FileInputStream keyStoreStream = new FileInputStream(trustStore.getFile())) {
//			keyStore.load(keyStoreStream, trustStorePassword.toCharArray());
//		}
//
//		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//		keyManagerFactory.init(keyStore, trustStorePassword.toCharArray());
//
//		TrustManagerFactory trustManagerFactory = TrustManagerFactory
//				.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//		trustManagerFactory.init(keyStore);
//
//		return SslContextBuilder.forClient().keyManager(keyManagerFactory).trustManager(trustManagerFactory).build();
//	}
}
