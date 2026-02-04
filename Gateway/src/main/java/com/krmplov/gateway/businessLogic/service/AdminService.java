package com.krmplov.gateway.businessLogic.service;

import com.krmplov.gateway.businessLogic.mapper.UserMapper;
import com.krmplov.gateway.dataAccess.dto.createRequest.CreateUserRequest;
import com.krmplov.gateway.dataAccess.httpRequest.AccountHttpRequest;
import com.krmplov.gateway.dataAccess.httpRequest.TransactionHttpRequest;
import com.krmplov.gateway.dataAccess.httpRequest.UserHttpRequest;
import com.krmplov.gateway.dataAccess.model.User;
import com.krmplov.gateway.dataAccess.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Transactional
@Service
@Data
public class AdminService {
    private final UserHttpRequest adminHttpRequest;
    private final UserRepository userRepository;
    private final AccountHttpRequest accountHttpRequest;
    private final TransactionHttpRequest transactionHttpRequest;

    public void saveClient(CreateUserRequest createUserRequest) {
        try {
            createUserRequest.setUserRole("ROLE_CLIENT");
            User user = UserMapper.fromCreateUserRequestToUser(createUserRequest);
            userRepository.save(user);
            adminHttpRequest.saveUser(createUserRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Client creation failed");
        }

    }

    public void saveAdmin(CreateUserRequest createUserRequest) {
        try {
            createUserRequest.setUserRole("ROLE_ADMIN");
            User user = UserMapper.fromCreateUserRequestToUser(createUserRequest);
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Client creation failed");
        }
    }

    public ResponseEntity<?> filterUsers(String hairColor, String gender) {
        try {
             return adminHttpRequest.filterUsers(hairColor, gender);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Client creation failed");
        }
    }

    public ResponseEntity<?> getUserByLogin(String login) {
        try {
            return adminHttpRequest.getUserByLogin(login);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Client creation failed");
        }
    }

    public ResponseEntity<?> listAccounts() {
        try {
            return accountHttpRequest.listAccounts();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Client creation failed");
        }
    }

    public ResponseEntity<?> getAccountById(Long id) {
        try {
            return accountHttpRequest.getAccountById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Client creation failed");
        }
    }

    public ResponseEntity<?> getTransactionById(@PathVariable("id") Long id) {
        try {
            return transactionHttpRequest.getTransactionById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Client creation failed");
        }
    }

    public ResponseEntity<?> listTransactions() {
        try {
            return transactionHttpRequest.listTransactions();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Client creation failed");
        }
    }
}
