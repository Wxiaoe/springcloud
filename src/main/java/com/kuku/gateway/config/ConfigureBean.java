package com.kuku.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Configuration;

/**
 * @author wxf
 */
@Configuration
public class ConfigureBean {

    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                .path("/get")
                .filters(f -> f.addRequestHeader("Hello", "World"))
                .uri("")
        )
                .route(p -> p.host("").filters(f -> f.hystrix(config -> config.setName("").setFallbackUri(""))).uri(""))
                .build();
    }

}
