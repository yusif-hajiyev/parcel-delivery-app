package az.guavapay.auth;

import az.guavapay.auth.client.UserClient;
import az.guavapay.auth.exception.AuthException;
import az.guavapay.auth.model.AuthRequest;
import az.guavapay.auth.model.AuthResponse;
import az.guavapay.auth.model.UserView;
import az.guavapay.auth.security.JwtResponse;
import az.guavapay.auth.security.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static az.guavapay.auth.model.ExceptionCode.FORBIDDEN;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserClient userClient;

    public AuthServiceImpl(JwtTokenUtil jwtTokenUtil, UserClient userClient) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userClient = userClient;
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        log.info("******login start*****");
        UserView userView = userClient.getUser(authRequest);
        Optional.ofNullable(userView)
                .orElseThrow(() -> new AuthException(FORBIDDEN.getMessage(), FORBIDDEN.getCode()));
        String token = jwtTokenUtil.generateToken(authRequest.getUsername(), userView.getRoleName());
        log.info("******login end*****");
        return AuthResponse.builder()
                .userId(userView.getId())
                .token(token)
                .build();
    }

    @Override
    public JwtResponse validateToken(String token, String username) {
        return jwtTokenUtil.validateToken(token, username);
    }

}
