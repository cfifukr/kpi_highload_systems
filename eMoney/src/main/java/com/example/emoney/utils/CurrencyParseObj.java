package com.example.emoney.utils;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyParseObj {

    @SerializedName("txt")
    private String name;

    @SerializedName("rate")
    private double rate;

    @SerializedName("cc")
    private String currencyCode;

    @Override
    public String toString() {
        return currencyCode + "(" + name + "): " + rate + ";";
    }
}
