package com.example.emoney.dtos.pageable;

import com.example.emoney.dtos.response.CommentResponseDto;

import java.util.List;

public class CommentPageDto extends PageDto {
    public CommentPageDto(Long total, Integer totalPage, Integer currentPage, List<CommentResponseDto> list) {
        super(total, totalPage, currentPage, list);
    }
}
