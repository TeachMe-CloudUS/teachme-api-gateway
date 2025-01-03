package us.cloud.teachme.gateway.routes;

import static org.springframework.cloud.gateway.server.mvc.filter.Bucket4jFilterFunctions.rateLimit;
import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RequestPredicates.path;

import java.net.URI;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class CertificationServiceRoute {

  @Value("${services.certification-service}")
  private String CERTIFICATION_SERVICE;

  @Bean
  RouterFunction<ServerResponse> certificationRoutes() {
    return GatewayRouterFunctions.route("auth-service")
        .route(path("/api/v1/certify/**").or(path("/api/v1/certificates/**")), http(CERTIFICATION_SERVICE))
        .filter(rateLimit(c -> c.setCapacity(100)
            .setPeriod(Duration.ofMinutes(1))
            .setKeyResolver(request -> request.remoteAddress().get().getAddress().getHostAddress())))
        .filter(circuitBreaker("auth-service", URI.create("forward:/fallback")))
        .build();
  }

  @Bean
  RouterFunction<ServerResponse> certificationSwaggerRoutes() {
    return GatewayRouterFunctions.route("certification-service-swagger")
        .route(path("/swagger/certification-service/**"), http(CERTIFICATION_SERVICE))
        .build();
  }

}
