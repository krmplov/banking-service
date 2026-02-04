package com.krmplov.services;

import com.krmplov.createRequest.CreateTransactionRequest;
import com.krmplov.dto.TransactionDto;
import com.krmplov.exceptions.BusinessException;
import com.krmplov.mappers.TransactionMapper;
import com.krmplov.models.Message;
import com.krmplov.models.OperationType;
import com.krmplov.models.Transaction;
import com.krmplov.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository _transactionRepository;

    public List<TransactionDto> listTransactions() {
        try {
            List<Transaction> transactions = _transactionRepository.findAll();
            List<TransactionDto> transactionDtos = new ArrayList<>();
            for (Transaction transaction : transactions) {
                transactionDtos.add(TransactionMapper.toDto(transaction));
            }
            return transactionDtos;
        } catch (Exception e) {
            throw new BusinessException(Message.DbError);
        }

    }

    public TransactionDto getTransactionById(Long id) {
        Transaction transaction = _transactionRepository.findById(id).orElseThrow(() -> new BusinessException(Message.TransactionNotFound));
        return TransactionMapper.toDto(transaction);
    }

    public void saveTransaction(CreateTransactionRequest transaction) {
        try {
            _transactionRepository.save(TransactionMapper.toEntity(transaction));
        } catch (Exception e) {
            throw new BusinessException(Message.DbError);
        }

    }

    public void deleteTransaction(Long id) {
        if (_transactionRepository.existsById(id)) {
            _transactionRepository.deleteById(id);
        }
        throw new BusinessException(Message.TransactionNotFound);
    }

    public List<TransactionDto> filterTransactions(OperationType type, Long accountId) {
        try {
            List<Transaction> transactions = _transactionRepository.findAll().stream()
                    .filter(t -> type == null || t.get_description() == type)
                    .filter(t -> accountId == null || t.getAccount().get_id().equals(accountId))
                    .toList();
            List<TransactionDto> transactionDtos = new ArrayList<>();
            for (Transaction transaction : transactions) {
                transactionDtos.add(TransactionMapper.toDto(transaction));
            }
            return transactionDtos;
        } catch (Exception e) {
            throw new BusinessException(Message.DbError);
        }
    }
}
