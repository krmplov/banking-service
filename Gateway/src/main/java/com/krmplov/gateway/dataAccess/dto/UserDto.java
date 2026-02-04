package com.krmplov.gateway.dataAccess.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String _login;
    private String _name;
    private int _age;
    private String _gender;
    private String _hairColor;
    private List<Long> _accounts;
    private List<String> _friendships;
}