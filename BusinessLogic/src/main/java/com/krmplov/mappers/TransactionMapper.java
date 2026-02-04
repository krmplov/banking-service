package com.krmplov.mappers;

import com.krmplov.createRequest.CreateTransactionRequest;
import com.krmplov.dto.TransactionDto;
import com.krmplov.models.Account;
import com.krmplov.models.Transaction;

import java.time.LocalDateTime;

public class TransactionMapper {
    public static Transaction toEntity(CreateTransactionRequest transaction) {
        Transaction transactionEntity = new Transaction();
        transactionEntity.set_date(LocalDateTime.now());
        transactionEntity.set_description(transaction.get_description());
        transactionEntity.set_amount(transaction.get_amount());
        transactionEntity.set_loginTo(transaction.get_loginTo());
        transactionEntity.set_loginFrom(transaction.get_loginFrom());
        transactionEntity.setAccount(new Account());
        transactionEntity.getAccount().set_id(transaction.get_account());

        return transactionEntity;
    }

    public static TransactionDto toDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.set_date(transaction.get_date());
        transactionDto.set_description(transaction.get_description());
        transactionDto.set_amount(transaction.get_amount());
        transactionDto.set_loginTo(transaction.get_loginTo());
        transactionDto.set_loginFrom(transaction.get_loginFrom());
        transactionDto.set_id(transaction.get_id());
        transactionDto.setAccount(transaction.getAccount().get_id().toString());

        return transactionDto;
    }
}
