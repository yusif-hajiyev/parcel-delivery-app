package az.guavapay.user.client;

import az.guavapay.user.security.JwtResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-auth", url = "${client.ms-auth.endpoint}")
public interface AuthClient {
    @GetMapping("/validate")
    JwtResponse validateToken(@RequestParam String token,
                              @RequestParam String username);
}
