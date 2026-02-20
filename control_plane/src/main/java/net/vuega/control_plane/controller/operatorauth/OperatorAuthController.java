package net.vuega.control_plane.controller.operatorauth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.vuega.control_plane.dto.operatorauth.AuthResponse;
import net.vuega.control_plane.dto.operatorauth.Login;
import net.vuega.control_plane.dto.operatorauth.Register;
import net.vuega.control_plane.service.operatorauth.OperatorAuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class OperatorAuthController {

    private final OperatorAuthService operatorAuthService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Register request) {
        operatorAuthService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody Login request) {
        return ResponseEntity.ok(operatorAuthService.login(request));
    }
}