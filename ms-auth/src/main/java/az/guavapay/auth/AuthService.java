package az.guavapay.auth;

import az.guavapay.auth.model.AuthRequest;
import az.guavapay.auth.model.AuthResponse;
import az.guavapay.auth.security.JwtResponse;

public interface AuthService {

    AuthResponse login(AuthRequest authRequest);

    JwtResponse validateToken(String token, String username);
}
