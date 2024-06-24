package com.example.emoney.dtos.pageable;

import com.example.emoney.dtos.TransactionResponseDto;

import java.util.List;

public class TransactionPageDto extends PageDto {

    public TransactionPageDto(Long total, Integer totalPage, Integer currentPage, List<TransactionResponseDto> list) {
        super(total, totalPage, currentPage, list);
    }
}
