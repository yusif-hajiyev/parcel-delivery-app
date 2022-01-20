package az.guavapay.auth.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {

    boolean isValidToken;
    String roleName;
}
