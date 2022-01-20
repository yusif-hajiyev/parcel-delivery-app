package com.guavapay.parcel.client;

import com.guavapay.parcel.model.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-auth", url = "${client.ms-user.endpoint}")
@Component
public interface UserClient {

    @GetMapping("/get-user")
    UserResponse getUserById(@RequestParam("id") Long id);
}
