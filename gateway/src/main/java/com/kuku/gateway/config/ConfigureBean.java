package com.kuku.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wxf
 */
@Configuration
public class ConfigureBean {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                .path("/get")
                .filters(f -> f.addRequestHeader("Hello", "World"))
                .uri("http://httpbin.org:80")
                )
                .route(p -> p
                        .host("*.hystrix.com")
//                        .filters(f -> f.hystrix(config -> config.setName("mycmd").setFallbackUri("/fallback")))
                        .uri("http://httpbin.org:80")
                )
                .build();
    }

}
