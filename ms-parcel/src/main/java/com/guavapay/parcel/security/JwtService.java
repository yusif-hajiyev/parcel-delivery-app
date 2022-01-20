package com.guavapay.parcel.security;

import com.guavapay.parcel.client.AuthClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class JwtService {

    private final AuthClient authClient;

    public JwtService(AuthClient authClient) {
        this.authClient = authClient;
    }

    public boolean isValidToken(String token, String username) {
        log.info("isValidToken start");
        try {
            JwtResponse response =  authClient.validateToken(token, username);
            log.info("isValidToken end");
            return !isNull(response) && response.isValidToken;
        } catch (Exception ex) {
            log.error("error occurred", ex);
            return false;
        }
    }

    public boolean hasPermission(String token, String username, String role) {
        log.info("hasPermission start");
        try {
            JwtResponse response =  authClient.validateToken(token, username);
            log.info("hasPermission end");
            return !isNull(response) && response.isValidToken && role.equals(response.roleName);
        } catch (Exception ex) {
            log.error("error occurred", ex);
            return false;
        }
    }
}
