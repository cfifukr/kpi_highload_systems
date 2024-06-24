package com.example.emoney.dtos.pageable;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@Data
abstract class PageDto {
    Long total;
    Integer totalPage;
    Integer currentPage;
    List<?> list;

    public PageDto(Long total, Integer totalPage, Integer currentPage, List<?> list) {
        this.total = total;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.list = list;
    }


}
