package com.guavapay.parcel.config;

import com.guavapay.parcel.client.AuthClient;
import com.guavapay.parcel.client.CourierClient;
import com.guavapay.parcel.client.UserClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = {
        AuthClient.class, UserClient.class, CourierClient.class
})
public class FeignDefaultConfig {

}