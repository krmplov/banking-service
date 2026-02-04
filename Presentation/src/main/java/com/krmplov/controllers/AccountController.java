package com.krmplov.controllers;

import com.krmplov.createRequest.CreateAccountRequest;
import com.krmplov.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "account management", description = "operations with bank accounts")
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService _accountService;

    @Operation(summary = "get all accounts", description = "returns all accounts details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public ResponseEntity<?> listAccounts() {
        return ResponseEntity.ok(_accountService.listAccounts());
    }

    @Operation(summary = "get account by ID", description = "returns account details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getAccountById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(_accountService.getAccountById(id));
    }

    @GetMapping("/user/{login}")
    public ResponseEntity<?> getAccountByLogin(@PathVariable("login") String login) {
        return ResponseEntity.ok(_accountService.getAccountByLogin(login));
    }

    @Operation(summary = "save account", description = "save account in Database")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateAccountRequest account) {
        _accountService.saveAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "delete account by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable("id") Long id) {
        _accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "withdrawal operation", description = "withdrawal from the account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("withdrawal")
    public ResponseEntity<?> withdrawal(@RequestParam Long id, @RequestParam double amount) {
        _accountService.withdrawal(id, amount);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "remittance operation",
            description = "remittance from one account to another")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("remittance")
    public ResponseEntity<?> remittance(@RequestParam Long idFrom,
                                           @RequestParam Long idTo,
                                           @RequestParam double amount) {
        _accountService.remittance(idFrom, idTo, amount);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "replenishment operation", description = "replenishment from the account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("replenishment")
    public ResponseEntity<?> replenishment(@RequestParam Long id, @RequestParam double amount) {
        _accountService.replenishment(id, amount);
        return ResponseEntity.ok().build();
    }
}
