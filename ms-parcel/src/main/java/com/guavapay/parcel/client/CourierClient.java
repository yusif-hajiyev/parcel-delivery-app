package com.guavapay.parcel.client;

import com.guavapay.parcel.model.request.ChangeCourierStatusRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-auth", url = "${client.ms-user.endpoint}")
@Component
public interface CourierClient {

    @PutMapping("/courier/change-status")
    void changeCourierStatus(@RequestBody ChangeCourierStatusRequest request);
}
