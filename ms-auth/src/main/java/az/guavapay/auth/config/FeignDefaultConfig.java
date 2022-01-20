package az.guavapay.auth.config;

import az.guavapay.auth.client.UserClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = {
        UserClient.class
})
public class FeignDefaultConfig {

}