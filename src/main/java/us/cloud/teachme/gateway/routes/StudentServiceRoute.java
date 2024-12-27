package us.cloud.teachme.gateway.routes;

import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RequestPredicates.path;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class StudentServiceRoute {

  @Value("${services.student-service}")
  private String STUDENT_SERVICE;

  @Bean
  RouterFunction<ServerResponse> studentRoutes() {
    return GatewayRouterFunctions.route("student-service")
      .route(path("/api/v1/students/**"), http(STUDENT_SERVICE))
      .filter(circuitBreaker("auth-service", URI.create("forward:/fallback")))
      .build();
  }
  
}
