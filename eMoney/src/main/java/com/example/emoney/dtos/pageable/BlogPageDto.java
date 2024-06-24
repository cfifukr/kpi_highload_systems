package com.example.emoney.dtos.pageable;

import com.example.emoney.dtos.response.CommentResponseDto;

import java.util.List;

public class BlogPageDto extends PageDto{

    public BlogPageDto(Long total, Integer totalPage, Integer currentPage, List<?> list) {
        super(total, totalPage, currentPage, list);
    }

}
