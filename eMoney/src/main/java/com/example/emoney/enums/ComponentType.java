package com.example.emoney.enums;

import lombok.Getter;

@Getter
public enum ComponentType {
    HEADER("HEADER"),
    TEXT("TEXT"),
    IMAGE("IMAGE");

    private final String value;

    ComponentType(String value) {
        this.value = value;
    }
    
}
