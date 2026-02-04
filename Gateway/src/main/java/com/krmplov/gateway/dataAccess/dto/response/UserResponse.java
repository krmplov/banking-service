package com.krmplov.gateway.dataAccess.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private String _login;
    private String _name;
    private int _age;
    private String _gender;
    private String _hairColor;
}
