package com.krmplov.services;

import com.krmplov.createRequest.CreateAccountRequest;
import com.krmplov.dto.AccountDto;
import com.krmplov.dto.AccountTopicDto;
import com.krmplov.exceptions.BusinessException;
import com.krmplov.kafka.KafkaProducer;
import com.krmplov.mappers.AccountMapper;
import com.krmplov.models.Account;
import com.krmplov.models.Message;
import com.krmplov.models.TopicMessage;
import com.krmplov.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository _accountRepository;
    private final BankingTransactionService _bankingTransactionService;
    private final KafkaProducer kafkaProducer;

    public List<AccountDto> listAccounts() {
        try {
            List<Account> accounts = _accountRepository.findAll();
            List<AccountDto> accountDtos = new ArrayList<>();
            for (Account account : accounts) {
                accountDtos.add(AccountMapper.toDto(account));
            }
            return accountDtos;
        } catch (Exception e) {
            throw new BusinessException(Message.DbError);
        }

    }

    public AccountDto getAccountById(Long id) {
        Account account = _accountRepository.findById(id).orElseThrow(() -> new BusinessException(Message.AccountNotFound));
        return AccountMapper.toDto(account);
    }

    public List<AccountDto> getAccountByLogin(String login) {
        List<Account> accounts = _accountRepository.getAccountsBy_user__login(login).orElseThrow(() -> new BusinessException(Message.AccountNotFound));
        List<AccountDto> accountDtos = new ArrayList<>();
        for (Account account : accounts) {
            accountDtos.add(AccountMapper.toDto(account));
        }
        return accountDtos;
    }

    public void saveAccount(CreateAccountRequest account) {
        try {

            Account saved = _accountRepository.save(AccountMapper.toEntity(account));
            AccountTopicDto accountTopicDto = new AccountTopicDto();
            accountTopicDto.setMessage(TopicMessage.ACCOUNT_CREATED.getMessage());
            kafkaProducer.send(saved.get_id().toString(), accountTopicDto, TopicMessage.ACCOUNT_CREATED.getTopic());

        } catch (Exception e) {
            throw new BusinessException(Message.DbError);
        }
    }

    public void deleteAccount(Long id) {
        if (_accountRepository.existsById(id)) {
            _accountRepository.deleteById(id);
        }
        throw new BusinessException(Message.AccountNotFound);
    }

    public void remittance(Long fromAccountID, Long toAccountID, double amount) {
        _bankingTransactionService.remittance(fromAccountID, toAccountID, amount);
        AccountTopicDto accountTopicDto = new AccountTopicDto();
        accountTopicDto.setMessage(TopicMessage.ACCOUNT_REMITTANCE.format(amount, fromAccountID, toAccountID));
        kafkaProducer.send(fromAccountID.toString(), accountTopicDto, TopicMessage.ACCOUNT_REMITTANCE.getTopic());
    }

    public void withdrawal(Long id, double amount) {
        _bankingTransactionService.withdrawal(id, amount);
        AccountTopicDto accountTopicDto = new AccountTopicDto();
        accountTopicDto.setMessage(TopicMessage.ACCOUNT_WITHDRAWAL.getMessage());
        kafkaProducer.send(id.toString(), accountTopicDto, TopicMessage.ACCOUNT_WITHDRAWAL.getTopic());
    }

    public void replenishment(Long id, double amount) {
        _bankingTransactionService.replenishment(id, amount);
        AccountTopicDto accountTopicDto = new AccountTopicDto();
        accountTopicDto.setMessage(TopicMessage.ACCOUNT_REPLENISHMENT.getMessage());
        kafkaProducer.send(id.toString(), accountTopicDto, TopicMessage.ACCOUNT_REPLENISHMENT.getTopic());
    }
}
