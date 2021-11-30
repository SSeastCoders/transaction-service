package com.ss.eastcoderbank.transactionapi.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
public class TransactionOptions {
    private final String search;
    private final LocalDate fromDate;
    private final LocalDate toDate;
    private final Float fromAmount;
    private final Float toAmount;
}
