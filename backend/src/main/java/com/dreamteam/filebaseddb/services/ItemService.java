package com.dreamteam.filebaseddb.services;

import com.dreamteam.filebaseddb.entities.Item;
import com.dreamteam.filebaseddb.exceptions.DuplicatedItemException;
import com.dreamteam.filebaseddb.exceptions.IllegalItemFormatException;
import com.dreamteam.filebaseddb.exceptions.ItemNotFoundException;
import com.dreamteam.filebaseddb.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public void addItem(Item item) {
        Optional<Item> duplicatedItem = itemRepository.findItemById(item.getId());
        if (duplicatedItem.isPresent()) {
            throw new DuplicatedItemException();
        } else {
            itemRepository.save(item);
        }
    }

    public void editItem(Long oldItemId, Item item) {
        deleteItem(oldItemId);
        addItem(item);
    }

    public void deleteItem(Long id) {
        Optional<Item> itemOpt = itemRepository.findItemById(id);
        if (itemOpt.isEmpty()) {
            throw new ItemNotFoundException();
        } else {
            itemRepository.deleteById(id);
        }
    }

    public List<Item> searchItems(String searchQuery) {
        String[] parts = searchQuery.split("=");
        String field = parts[0].toLowerCase();
        String value = parts[1].toLowerCase();

        return switch (field) {
            case "id" -> itemRepository.findAllItemsById(Long.parseLong(value));
            case "name" -> itemRepository.findAllItemsByName(value);
            case "amountavailable" -> itemRepository.findAllItemsByAmountAvailable(Long.parseLong(value));
            case "price" -> itemRepository.findAllItemsByPrice(Integer.parseInt(value));
            case "color" -> itemRepository.findAllItemsByColor(value);
            case "refurbished" -> itemRepository.findAllItemsByRefurbished(Boolean.parseBoolean(value));
            default -> throw new IllegalItemFormatException();
        };
    }

    public void deleteItemsBySearchQuery(String searchQuery) {
        String[] parts = searchQuery.split("=");
        String field = parts[0].toLowerCase();
        String value = parts[1].toLowerCase();

        switch (field) {
            case "id" -> itemRepository.deleteById(Long.parseLong(value));
            case "name" -> itemRepository.deleteAllItemsByName(value);
            case "amountavailable" -> itemRepository.deleteAllItemsByAmountAvailable(Long.parseLong(value));
            case "price" -> itemRepository.deleteAllItemsByPrice(Integer.parseInt(value));
            case "color" -> itemRepository.deleteAllItemsByColor(value);
            case "refurbished" -> itemRepository.deleteAllItemsByRefurbished(Boolean.parseBoolean(value));
            default -> throw new IllegalItemFormatException();
        }
    }

    public void deleteAllItems() {
        itemRepository.deleteAllItems();
    }
}
