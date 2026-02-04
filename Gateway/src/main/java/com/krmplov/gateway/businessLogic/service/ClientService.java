package com.krmplov.gateway.businessLogic.service;

import com.krmplov.gateway.dataAccess.dto.AccountDto;
import com.krmplov.gateway.dataAccess.dto.createRequest.CreateAccountRequest;
import com.krmplov.gateway.dataAccess.httpRequest.AccountHttpRequest;
import com.krmplov.gateway.dataAccess.httpRequest.TransactionHttpRequest;
import com.krmplov.gateway.dataAccess.httpRequest.UserHttpRequest;
import com.krmplov.gateway.dataAccess.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Data
@Transactional
public class ClientService {
    private final UserHttpRequest userHttpRequest;
    private final AccountHttpRequest accountHttpRequest;
    private final TransactionHttpRequest transactionHttpRequest;

    public ResponseEntity<?> getInfo() {
        try {
            String login = SecurityContextHolder.getContext().getAuthentication().getName();
            return userHttpRequest.getUserByLogin(login);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Client creation failed");
        }
    }

    public ResponseEntity<?> getClientAccounts() {
        try {
            String login = SecurityContextHolder.getContext().getAuthentication().getName();
            return accountHttpRequest.getAccountByLogin(login);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Client creation failed");
        }
    }

    public ResponseEntity<?> getAccountById(Long id) {
        try {
            ResponseEntity<?> responseEntity = getClientAccounts();
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                List<AccountDto> accounts = (List<AccountDto>) responseEntity.getBody();
                for (AccountDto account : accounts) {
                    if (account.get_id().equals(id)) {
                        return new ResponseEntity<>(account, HttpStatus.OK);
                    }
                }
                throw new ResponseStatusException(HttpStatus.OK, "Account with id " + id + " not found");
            }
            return accountHttpRequest.getAccountById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Client creation failed");
        }
    }

    public ResponseEntity<?> addFriend(String login) {
        try {
            String userFirst = SecurityContextHolder.getContext().getAuthentication().getName();
            return userHttpRequest.addFriend(userFirst, login);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Client creation failed");
        }
    }

    public ResponseEntity<?> removeFriend(String login) {
        try {
            String userFirst = SecurityContextHolder.getContext().getAuthentication().getName();
            return userHttpRequest.removeFriend(userFirst, login);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Client creation failed");
        }
    }

    public ResponseEntity<?> createAccount() {
        try {
            CreateAccountRequest createAccountRequest = new CreateAccountRequest();
            String user = SecurityContextHolder.getContext().getAuthentication().getName();
            createAccountRequest.set_user(user);
            return accountHttpRequest.createAccount(createAccountRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Client creation failed");
        }
    }

    public ResponseEntity<?> remittance(Long idFrom,
                                        Long idTo,
                                        double amount) {
        try {
            boolean flag = false;
            String user = SecurityContextHolder.getContext().getAuthentication().getName();
            ResponseEntity<?> accounts = accountHttpRequest.getAccountByLogin(user);
            if (accounts.getStatusCode().equals(HttpStatus.OK)) {
                List<AccountDto> accountDtos = (List<AccountDto>) accounts.getBody();
                for (AccountDto accountDto : accountDtos) {
                    if (accountDto.get_id().equals(idFrom)) {
                        flag = true;
                    }
                }
            }
            if (!flag) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
            }
            return accountHttpRequest.remittance(idFrom, idTo, amount);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Client creation failed");
        }
    }

    public ResponseEntity<?> replenishment(Long id, double amount) {
        try {
            boolean flag = false;
            String user = SecurityContextHolder.getContext().getAuthentication().getName();
            ResponseEntity<?> accounts = accountHttpRequest.getAccountByLogin(user);
            if (accounts.getStatusCode().equals(HttpStatus.OK)) {
                List<AccountDto> accountDtos = (List<AccountDto>) accounts.getBody();
                for (AccountDto accountDto : accountDtos) {
                    if (accountDto.get_id().equals(id)) {
                        flag = true;
                    }
                }
            }
            if (!flag) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
            }
            return accountHttpRequest.replenishment(id, amount);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Client creation failed");
        }
    }

    public ResponseEntity<?> withdrawal(Long id, double amount) {
        try {
            boolean flag = false;
            String user = SecurityContextHolder.getContext().getAuthentication().getName();
            ResponseEntity<?> accounts = accountHttpRequest.getAccountByLogin(user);
            if (accounts.getStatusCode().equals(HttpStatus.OK)) {
                List<AccountDto> accountDtos = (List<AccountDto>) accounts.getBody();
                assert accountDtos != null;
                for (AccountDto accountDto : accountDtos) {
                    if (accountDto.get_id().equals(id)) {
                        flag = true;
                    }
                }
            }
            if (!flag) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
            }
            return accountHttpRequest.withdrawal(id, amount);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Client creation failed");
        }
    }


}
