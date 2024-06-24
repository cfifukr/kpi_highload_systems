package com.example.emoney.exceptions;



import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDto {


    @JsonProperty("errorCode")
    private Integer code;

    @JsonProperty("errorDescription")
    private String description;

    @JsonProperty("errorDate")
    private Date date;

    @JsonProperty("isException")
    private Boolean isException;

    public ExceptionDto(Integer code, String description) {
        this.code = code;
        this.description = description;
        this.date = new Date();
        this.isException = true;
    }
}
