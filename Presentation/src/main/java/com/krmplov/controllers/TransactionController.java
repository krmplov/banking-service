package com.krmplov.controllers;

import com.krmplov.createRequest.CreateTransactionRequest;
import com.krmplov.models.OperationType;
import com.krmplov.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "transaction management", description = "operations with bank transaction")
@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService _transactionService;

    @Operation(summary = "get all transactions", description = "returns all transactions details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public ResponseEntity<?> listTransactions() {
        return ResponseEntity.ok(_transactionService.listTransactions());
    }

    @Operation(summary = "get transaction by ID", description = "returns transaction details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(_transactionService.getTransactionById(id));
    }

    @Operation(summary = "save transaction", description = "save transaction in Database")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateTransactionRequest transaction) {
        _transactionService.saveTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "delete transaction by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("id") Long id) {
        _transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "filtering by operation type and account id",
            description = "returns a list of suitable transaction")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("filter")
    public ResponseEntity<?> filterTransactions(
            @RequestParam(required = false) OperationType operationType,
            @RequestParam(required = false) Long accountId) {
        return ResponseEntity.ok(_transactionService.filterTransactions(operationType, accountId));
    }
}
