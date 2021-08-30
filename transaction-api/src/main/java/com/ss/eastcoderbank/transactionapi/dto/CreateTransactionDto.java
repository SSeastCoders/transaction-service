package com.ss.eastcoderbank.transactionapi.dto;

import com.ss.eastcoderbank.core.model.transaction.TransactionType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class CreateTransactionDto {
    @NotNull
    private Integer accountId;

    @NotNull
    private Float amount;

    @NotBlank
    private String description;

    @NotNull
    private TransactionType type;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "must be today or in the past")
    private LocalDate date;

    private Boolean pending;

    private Boolean succeeded;
}
