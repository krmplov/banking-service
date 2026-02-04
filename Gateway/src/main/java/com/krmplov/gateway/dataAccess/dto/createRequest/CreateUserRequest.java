package com.krmplov.gateway.dataAccess.dto.createRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    @NotNull
    private String login;

    @NotBlank
    private String name;

    @NotBlank
    private int age;

    @NotBlank
    private String gender;

    @NotBlank
    private String hairColor;

    @NotBlank
    private String password;

    private String userRole;
}
