package com.guavapay.parcel.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private Long userId;
    private Long roleId;
    private Long customerId;
    private Long courierId;
}
