package com.ss.eastcoderbank.transactionapi.mapper;

import com.ss.eastcoderbank.core.model.transaction.Transaction;
import com.ss.eastcoderbank.transactionapi.dto.CreateTransactionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CreateTransactionMapper {

    @Mapping(ignore = true, target = "account")
    Transaction mapToEntity(CreateTransactionDto createTransactionDto);
}
