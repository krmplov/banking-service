package com.krmplov.services;

import com.krmplov.createRequest.CreateTransactionRequest;
import com.krmplov.exceptions.BusinessException;
import com.krmplov.models.*;
import com.krmplov.repository.AccountRepository;
import com.krmplov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankingTransactionService {
    private final AccountRepository _accountRepository;
    private final TransactionService _transactionService;
    private final UserRepository _userRepository;

    public void remittance(Long fromAccountID, Long toAccountID, double amount) {
        if (amount <= 0) {
            throw new BusinessException(Message.InvalidAmount);
        }
        if (fromAccountID.equals(toAccountID)) {
            throw new BusinessException(Message.TheSameUser);
        }
        Account fromAccount = _accountRepository.findById(fromAccountID).orElseThrow(() -> new BusinessException(Message.AccountNotFound));
        Account toAccount = _accountRepository.findById(toAccountID).orElseThrow(() -> new BusinessException(Message.AccountNotFound));

        User fromUser = _userRepository.findById(fromAccount.get_user().get_login()).orElseThrow(() -> new BusinessException(Message.UserNotFound));
        User toUser = _userRepository.findById(toAccount.get_user().get_login()).orElseThrow(() -> new BusinessException(Message.UserNotFound));

        double commission = commission(fromUser, toUser, amount);
        double totalAmount = amount + commission;

        if (fromAccount.get_balance() < totalAmount) {
            throw new BusinessException(Message.InsufficientFunds);
        }
        fromAccount.set_balance(fromAccount.get_balance() - totalAmount);
        _accountRepository.save(fromAccount);

        toAccount.set_balance(toAccount.get_balance() + amount);
        _accountRepository.save(toAccount);

        CreateTransactionRequest remittance = new CreateTransactionRequest();
        remittance.set_account(fromAccount.get_id());
        remittance.set_amount(amount);
        remittance.set_description(OperationType.Remittance);
        remittance.set_loginFrom(fromUser.get_login());
        remittance.set_loginTo(toUser.get_login());
        _transactionService.saveTransaction(remittance);

        if (commission > 0) {
            CreateTransactionRequest commissionTx = new CreateTransactionRequest();
            commissionTx.set_account(fromAccount.get_id());
            commissionTx.set_amount(commission);
            commissionTx.set_description(OperationType.Commission);
            _transactionService.saveTransaction(commissionTx);
        }

    }

    private double commission(User fromUser, User toUser, double amount) {
        if (fromUser.get_login().equals(toUser.get_login())) {
            return 0;
        } else if (fromUser.get_friendships().contains(toUser.get_login())) {
            return amount * 0.03;
        } else {
            return amount * 0.10;
        }
    }

    public void withdrawal(Long id, double amount) {
        if (amount <= 0) {
            throw new BusinessException(Message.InvalidAmount);
        }
        _accountRepository.findById(id)
                .map(account -> {
                    if (account.get_balance() < amount) {
                        throw new BusinessException(Message.InsufficientFunds);
                    }

                    account.set_balance(account.get_balance() - amount);
                    _accountRepository.save(account);

                    CreateTransactionRequest transaction = new CreateTransactionRequest();
                    transaction.set_account(account.get_id());
                    transaction.set_amount(amount);
                    transaction.set_description(OperationType.Withdrawal);
                    _transactionService.saveTransaction(transaction);

                    return true;
                })
                .orElseThrow(() -> new BusinessException(Message.AccountNotFound));

    }

    public void replenishment(Long id, double amount) {
        if (amount <= 0) {
            throw new BusinessException(Message.InvalidAmount);
        }
        _accountRepository.findById(id)
                .map(account -> {
                    account.set_balance(account.get_balance() + amount);
                    _accountRepository.save(account);

                    CreateTransactionRequest transaction = new CreateTransactionRequest();
                    transaction.set_account(account.get_id());
                    transaction.set_amount(amount);
                    transaction.set_description(OperationType.Replenishment);
                    _transactionService.saveTransaction(transaction);

                    return true;
                })
                .orElseThrow(() -> new BusinessException(Message.AccountNotFound));
    }
}
