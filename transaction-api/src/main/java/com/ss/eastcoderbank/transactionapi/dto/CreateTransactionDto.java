package com.ss.eastcoderbank.transactionapi.dto;

import com.ss.eastcoderbank.core.model.transaction.TransactionType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CreateTransactionDto {
    @NotNull
    private Integer accountId;

    @NotNull
    private Double amount;

    @NotBlank
    private String description;

    @NotNull
    private TransactionType type;

    private LocalDateTime date;

    private Boolean pending;

    private Boolean succeeded;
}
