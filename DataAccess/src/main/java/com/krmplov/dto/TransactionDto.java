package com.krmplov.dto;

import com.krmplov.models.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private Long _id;
    private LocalDateTime _date;
    private OperationType _description;
    private double _amount;
    private String _loginTo;
    private String _loginFrom;
    private String account;
}
