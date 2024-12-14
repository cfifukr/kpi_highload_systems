package com.example.lab5v2.services;

import com.example.lab5v2.models.Item;
import com.example.lab5v2.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {
    private final ItemRepository itemRepository;

    public MainService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


}

