package az.guavapay.user.config;

import az.guavapay.user.client.AuthClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = {AuthClient.class})
public class FeignDefaultConfig {

}