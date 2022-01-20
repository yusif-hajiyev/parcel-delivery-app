package az.guavapay.auth.client;

import az.guavapay.auth.model.AuthRequest;
import az.guavapay.auth.model.UserView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-user", url = "${client.ms-user.endpoint}")
public interface UserClient {

    @PostMapping("/get-customer")
    UserView getUser(@RequestBody AuthRequest authRequest);
}
