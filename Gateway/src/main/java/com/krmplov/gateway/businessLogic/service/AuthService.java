package com.krmplov.gateway.businessLogic.service;

import com.krmplov.gateway.businessLogic.jwt.JwtService;
import com.krmplov.gateway.dataAccess.dto.createRequest.CreateLoginRequest;
import com.krmplov.gateway.dataAccess.dto.response.LoginResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse login(CreateLoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String login = authentication.getName();
        String role = authentication.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");

        String token = jwtService.generateToken(login, role);
        return new LoginResponse(token);
    }
}
