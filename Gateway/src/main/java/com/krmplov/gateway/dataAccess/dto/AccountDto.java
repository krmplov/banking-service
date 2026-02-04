package com.krmplov.gateway.dataAccess.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long _id;
    private double _balance;
    private String _user;
}
