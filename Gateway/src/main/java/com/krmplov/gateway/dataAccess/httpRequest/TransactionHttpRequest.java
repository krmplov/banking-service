package com.krmplov.gateway.dataAccess.httpRequest;

import com.krmplov.gateway.businessLogic.jwt.JwtService;
import com.krmplov.gateway.dataAccess.dto.TransactionDto;
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
public class TransactionHttpRequest {
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

    public ResponseEntity<?> getTransactionById(@PathVariable("id") Long id) {
        try {
            String url = String.format("%s/transactions/%s",
                    baseUrl,
                    id);

            return restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(createAdminHeaders()),
                    new ParameterizedTypeReference<TransactionDto>() {}
            );
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> listTransactions() {
        try {
            String url = String.format("%s/transactions",
                    baseUrl);

            return restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(createAdminHeaders()),
                    new ParameterizedTypeReference<List<TransactionDto>>() {}
            );
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}
