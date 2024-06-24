package com.example.emoney.enums;

public enum Currency {
    USD("USD"),
    UAH("UAH"),
    EUR("EUR");

    private final String value;

    Currency(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
