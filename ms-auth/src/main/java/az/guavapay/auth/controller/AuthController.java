package az.guavapay.auth.controller;

import az.guavapay.auth.model.AuthRequest;
import az.guavapay.auth.model.AuthResponse;
import az.guavapay.auth.security.JwtResponse;
import az.guavapay.auth.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @GetMapping("/validate")
    public JwtResponse validateToken(@RequestParam String token, @RequestParam String username) {
        return authService.validateToken(token, username);
    }
}
