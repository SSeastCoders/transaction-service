package com.ss.eastcoderbank.transactionapi.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TransactionOptions {
    private final String search;
    private final LocalDate fromDate;
    private final LocalDate toDate;
    private final Float fromAmount;
    private final Float toAmount;
}
