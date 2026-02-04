package com.krmplov.gateway.dataAccess.httpRequest;

import com.krmplov.gateway.businessLogic.jwt.JwtService;
import com.krmplov.gateway.dataAccess.dto.AccountDto;
import com.krmplov.gateway.dataAccess.dto.UserDto;
import com.krmplov.gateway.dataAccess.dto.createRequest.CreateAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountHttpRequest {
    private final RestTemplate restTemplate;
    private final JwtService jwtService;

    @Value("${bank-server.url}")
    private String baseUrl;

    private String generateAdminToken() {
        return jwtService.generateToken(
                "admin-service",
                "ADMIN"
        );
    }

    private HttpHeaders createAdminHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(generateAdminToken());
        return headers;
    }

    public ResponseEntity<?> getAccountById(Long id) {
        try {
            String url = String.format("%s/accounts/%s",
                    baseUrl,
                    id);

            return restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(createAdminHeaders()),
                    new ParameterizedTypeReference<AccountDto>() {}
            );
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> listAccounts() {
        try {
            String url = String.format("%s/accounts",
                    baseUrl);

            return restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(createAdminHeaders()),
                    new ParameterizedTypeReference<List<AccountDto>>() {}
            );
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getAccountByLogin(@PathVariable("login") String login) {
        try {
            String url = String.format("%s/accounts/user/%s",
                    baseUrl,
                    login);

            return restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(createAdminHeaders()),
                    new ParameterizedTypeReference<List<AccountDto>>() {}
            );
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> createAccount(CreateAccountRequest account) {
        try {
            String url = String.format("%s/accounts",
                    baseUrl);

            return restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(account, createAdminHeaders()),
                    Void.class
            );
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> remittance(Long idFrom,
                                        Long idTo,
                                        double amount) {
        try {
            String url = String.format("%s/accounts/remittance?idFrom=%s&idTo=%s&amount=%s",
                    baseUrl,
                    idFrom,
                    idTo,
                    amount);

            return restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(createAdminHeaders()),
                    Void.class
            );
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> replenishment(Long id,
                                           double amount) {
        try {
            String url = String.format("%s/accounts/replenishment?id=%s&amount=%s",
                    baseUrl,
                    id,
                    amount);

            return restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(createAdminHeaders()),
                    Void.class
            );
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> withdrawal(Long id,
                                        double amount) {
        try {
            String url = String.format("%s/accounts/withdrawal?id=%s&amount=%s",
                    baseUrl,
                    id,
                    amount);

            return restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(createAdminHeaders()),
                    Void.class
            );
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}
