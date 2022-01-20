package com.guavapay.parcel.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {

    boolean isValidToken;
    String roleName;
}
