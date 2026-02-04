package com.krmplov.dto;

import com.krmplov.models.Gender;
import com.krmplov.models.HairColor;
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
    private Gender _gender;
    private HairColor _hairColor;
    private List<Long> _accounts;
    private List<String> _friendships;
}
