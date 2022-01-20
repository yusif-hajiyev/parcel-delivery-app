package com.guavapay.parcel.client;

import com.guavapay.parcel.security.JwtResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-auth", url = "${client.ms-auth.endpoint}")
@Component
public interface AuthClient {

    @GetMapping("/validate")
    JwtResponse validateToken(@RequestParam String token,
                              @RequestParam String username);
}
