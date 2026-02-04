package com.krmplov.gateway.dataAccess.dto.createRequest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateLoginRequest {
    private String username;
    private String password;
}
