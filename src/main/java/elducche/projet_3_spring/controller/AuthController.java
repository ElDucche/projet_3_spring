package elducche.projet_3_spring.controller;

import java.lang.System.Logger;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import elducche.projet_3_spring.dto.AuthResponse;
import elducche.projet_3_spring.dto.LoginRequest;
import elducche.projet_3_spring.dto.RegisterRequest;
import elducche.projet_3_spring.services.AuthService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/test")
    public ResponseEntity<Map<String, String>> test(@RequestBody Map<String, String> data) {
        Logger.info("Corps reçu : " + data);
        return ResponseEntity.ok(data);
    }
}
