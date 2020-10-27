package com.dgs.springsecurityjwt.controller;

import com.dgs.springsecurityjwt.models.AuthenticationRequest;
import com.dgs.springsecurityjwt.models.AuthenticationResponse;
import com.dgs.springsecurityjwt.services.MyUserDetailsService;
import com.dgs.springsecurityjwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HelloResource {

    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @GetMapping("/homepage")
    public String home() {
        return "This is homepage!";
    }

    @GetMapping("/users")
    public String users() {
        return "Hello Users!";
    }

    @GetMapping("/admins")
    public String admins() {
        return "Hello Admins!";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            log.error("Incorrect username or password, exception = {} : {}", ex.getClass().getName(), ex.getMessage());
            throw new Exception("Incorrect username or password", ex);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(
                authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}