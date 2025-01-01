package us.cloud.teachme.gateway.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RequestPredicates.path;

@Configuration(proxyBeanMethods = false)
public class NotificationWebsocketServiceRoute {

    @Value("${services.notification-ws-service}")
    private String NOTIFICATION_WS_SERVICE;

    @Order(1)
    @Bean
    RouterFunction<ServerResponse> notificationWebsocketRoute() {
        return GatewayRouterFunctions.route("notification-ws-service")
                .route(path("/ws/v1/notifications/**"), http(NOTIFICATION_WS_SERVICE))
                .build();
    }

}
