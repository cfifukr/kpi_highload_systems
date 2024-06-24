package com.example.emoney.models.classes;


import com.example.emoney.enums.ComponentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Component {

    private ComponentType type;
    private String data;

}
