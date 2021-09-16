package com.ss.eastcoderbank.transactionapi.service;

import com.ss.eastcoderbank.core.exeception.AccountNotFoundException;
import com.ss.eastcoderbank.core.model.account.Account;
import com.ss.eastcoderbank.core.model.transaction.Transaction;
import com.ss.eastcoderbank.core.repository.AccountRepository;
import com.ss.eastcoderbank.core.repository.TransactionRepository;
import com.ss.eastcoderbank.core.transferdto.TransactionDto;
import com.ss.eastcoderbank.core.transfermapper.TransactionMapper;
import com.ss.eastcoderbank.transactionapi.dto.CreateTransactionDto;
import com.ss.eastcoderbank.transactionapi.mapper.CreateTransactionMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final CreateTransactionMapper createTransactionMapper;
    private final AccountRepository accountRepository;

    public Page<TransactionDto> getTransactionByAccount(Integer id, Integer page, Integer size) {
        return transactionRepository.findTransactionByAccountId(id, PageRequest.of(page, size)).map(transactionMapper::mapToDto);
    }

    public void postTransaction(CreateTransactionDto transaction) {
        Transaction entity = createTransactionMapper.mapToEntity(transaction);
        Account account = accountRepository.findById(transaction.getAccountId()).orElseThrow(AccountNotFoundException::new);
        if (account.getBalance() + entity.getAmount() >= 0) {
            account.setBalance((float) (account.getBalance() + entity.getAmount()));
            entity.setSucceeded(true); // might need to change this if using stripe
        } else {
            entity.setSucceeded(false);
        }
        entity.setAccount(account);
        accountRepository.save(account);
        transactionRepository.save(entity);
    }

    public void postManyTransactions(List<CreateTransactionDto> transactions) {
       transactions.forEach(this::postTransaction);
    }
}
