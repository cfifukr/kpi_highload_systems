package com.example.lab3.service;

import com.example.lab3.models.Item;
import com.example.lab3.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Integer incrementLikes(Long itemId) {
        Item item = itemRepository.findById(itemId).get();
        item.setLikes(item.getLikes() + 1);

        return itemRepository.save(item).getLikes();
    }


    public synchronized Integer incrementLikesSynchronized(Long itemId) {
        Item item = itemRepository.findById(itemId).get();
        item.setLikes(item.getLikes() + 1);

        return itemRepository.save(item).getLikes();
    }

}