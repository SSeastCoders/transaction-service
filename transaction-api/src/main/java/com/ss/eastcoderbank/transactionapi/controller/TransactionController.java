package com.ss.eastcoderbank.transactionapi.controller;

import com.ss.eastcoderbank.core.transferdto.TransactionDto;
import com.ss.eastcoderbank.transactionapi.dto.CreateTransactionDto;
import com.ss.eastcoderbank.transactionapi.service.TransactionOptions;
import com.ss.eastcoderbank.transactionapi.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/transactions")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    @GetMapping("/{id}")
    public Page<TransactionDto> getTransactionByDates(
            @PathVariable Integer id,
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam Optional<String> search,
            @RequestParam("fromDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    Optional<LocalDate> start,
            @RequestParam("toDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    Optional<LocalDate> end,
            @RequestParam Optional<Float> fromAmount,
            @RequestParam Optional<Float> toAmount) {
        String description = search.orElse("");
        LocalDate startwith = start.orElse(LocalDate.MIN);
        LocalDate endswith = end.orElse(LocalDate.of(9999, 12, 31));
        Float startAmount = fromAmount.orElse(-Float.MAX_VALUE);
        Float endAmount = toAmount.orElse(Float.MAX_VALUE);
        TransactionOptions options = new TransactionOptions(description, startwith, endswith, startAmount, endAmount);
        return transactionService.getTransactionsByOptions(id, page, size, options);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postTransaction(@Valid @RequestBody CreateTransactionDto transaction) {
        transactionService.postTransaction(transaction);
    }

    @PostMapping("/populate")
    @ResponseStatus(HttpStatus.CREATED)
    public void populateTransaction(@Valid @RequestBody List<CreateTransactionDto> transactions) {
        transactionService.postManyTransactions(transactions);
    }

    @GetMapping("/health")
    @ResponseStatus(HttpStatus.OK)
    public void healthCheck() {}
}
