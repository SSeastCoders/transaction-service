package com.ss.eastcoderbank.transactionapi.controller;

import com.ss.eastcoderbank.core.transferdto.TransactionDto;
import com.ss.eastcoderbank.transactionapi.dto.CreateTransactionDto;
import com.ss.eastcoderbank.transactionapi.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/{id}")
    public Page<TransactionDto> getTransactionByAccount(@PathVariable Integer id, @RequestParam Integer page, @RequestParam Integer size) {
        return transactionService.getTransactionByAccount(id, page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postTransaction(@Valid @RequestBody CreateTransactionDto transaction) {
        transactionService.postTransaction(transaction);
    }
}
