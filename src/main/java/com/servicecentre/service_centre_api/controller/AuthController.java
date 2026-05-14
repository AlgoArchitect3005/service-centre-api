package com.servicecentre.service_centre_api.controller;

import com.servicecentre.service_centre_api.model.User;
import com.servicecentre.service_centre_api.security.JwtUtil;
import com.servicecentre.service_centre_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody Map<String, String> body) {

        // Username + password verify karo
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                body.get("username"),
                body.get("password")
            )
        );

        // Token generate karo
        User user = (User) authentication.getPrincipal();
        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole().name()
        );

        return ResponseEntity.ok(Map.of("token", token));
    }
}