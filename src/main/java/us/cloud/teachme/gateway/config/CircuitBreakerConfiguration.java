package us.cloud.teachme.gateway.config;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class CircuitBreakerConfiguration {

  @Bean
  Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
    return factory -> {
      factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
      .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build())
      .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
      .build());
    };
  }
  
}
