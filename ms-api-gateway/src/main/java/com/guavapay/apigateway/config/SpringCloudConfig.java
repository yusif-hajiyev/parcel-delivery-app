package com.guavapay.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/ms-auth/**")
                        .uri("http://localhost:8081/")
                        .id("msAuthModule"))
                .route(r -> r.path("/ms-user/**")
                        .uri("http://localhost:8082/")
                        .id("msUserModule"))
                .route(r -> r.path("/ms-parcel/**")
                        .uri("http://localhost:8083/")
                        .id("msParcelModule"))
                .build();
    }

}