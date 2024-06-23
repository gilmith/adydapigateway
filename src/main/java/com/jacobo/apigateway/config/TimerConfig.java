package com.jacobo.apigateway.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;

@Configuration
public class TimerConfig {
	
	@Bean
	public TimeLimiterRegistry timeLimiterRegistry() {
	   return TimeLimiterRegistry.of(TimeLimiterConfig.custom()
	            .timeoutDuration(Duration.ofSeconds(50))
	            .cancelRunningFuture(false)
	            .build());
	}

}
