package us.cloud.teachme.gateway.routes;

import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RequestPredicates.path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class AuthServiceRoute {

  @Value("${services.auth-service}")
  private String AUTH_SERVICE;

  @Bean
  RouterFunction<ServerResponse> authRoutes() {
    return GatewayRouterFunctions.route("auth-service")
      .route(path("/api/v1/users/**").or(path("/api/v1/auth/**")), http(AUTH_SERVICE))
      .build();
  }
  
}