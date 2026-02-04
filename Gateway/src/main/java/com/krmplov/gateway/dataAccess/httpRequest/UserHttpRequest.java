package com.krmplov.gateway.dataAccess.httpRequest;

import com.krmplov.gateway.businessLogic.jwt.JwtService;
import com.krmplov.gateway.businessLogic.mapper.UserMapper;
import com.krmplov.gateway.dataAccess.dto.AccountDto;
import com.krmplov.gateway.dataAccess.dto.TransactionDto;
import com.krmplov.gateway.dataAccess.dto.UserDto;
import com.krmplov.gateway.dataAccess.dto.createRequest.CreateUserRequest;
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
public class UserHttpRequest {
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

    public ResponseEntity<?> saveUser(CreateUserRequest request) {
        try {
            System.out.println(baseUrl);
            return restTemplate.exchange(
                    baseUrl + "/users",
                    HttpMethod.POST,
                    new HttpEntity<>(UserMapper.fromCreateUserRequestToUserResponse(request), createAdminHeaders()),
                    Void.class
            );
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> filterUsers(String hairColor, String gender) {
        try {
            String url = String.format("%s/users/filter?hairColor=%s&gender=%s",
                    baseUrl,
                    hairColor,
                    gender);

            return restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(createAdminHeaders()),
                    new ParameterizedTypeReference<List<UserDto>>() {}
            );
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getUserByLogin(@PathVariable("login") String login) {
        try {
            String url = String.format("%s/users/%s",
                    baseUrl,
                    login);

            return restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(createAdminHeaders()),
                    new ParameterizedTypeReference<UserDto>() {}
            );
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> addFriend(String userFirst, String userSecond) {
        try {
            String url = String.format("%s/users/friends/?userFirst=%s&userSecond=%s",
                    baseUrl,
                    userFirst,
                    userSecond);

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

    public ResponseEntity<?> removeFriend(String userFirst, String userSecond) {
        try {
            String url = String.format("%s/users/remove_friend/?userFirst=%s&userSecond=%s",
                    baseUrl,
                    userFirst,
                    userSecond);

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