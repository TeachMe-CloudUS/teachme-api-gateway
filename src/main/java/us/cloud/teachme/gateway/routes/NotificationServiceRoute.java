package us.cloud.teachme.gateway.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;
import java.time.Duration;

import static org.springframework.cloud.gateway.server.mvc.filter.Bucket4jFilterFunctions.rateLimit;
import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RequestPredicates.path;

@Configuration(proxyBeanMethods = false)
public class NotificationServiceRoute {

    @Value("${services.notification-service}")
    private String NOTIFICATION_SERVICE;

    @Bean
    RouterFunction<ServerResponse> notificationRoutes() {
        return GatewayRouterFunctions.route("notification-service")
                .route(path("/api/v1/notifications/**"), http(NOTIFICATION_SERVICE))
                .filter(rateLimit(c -> c.setCapacity(100)
                        .setPeriod(Duration.ofMinutes(1))
                        .setKeyResolver(request -> request.remoteAddress().get().getAddress().getHostAddress())))
                .filter(circuitBreaker("auth-service", URI.create("forward:/fallback")))
                .build();
    }

}
