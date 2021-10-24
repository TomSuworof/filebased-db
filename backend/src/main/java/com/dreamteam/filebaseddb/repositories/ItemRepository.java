package com.dreamteam.filebaseddb.repositories;

import com.dreamteam.filebaseddb.entities.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    List<Item> findAll();

    void save(Item item);

    void deleteById(Long id);

    Optional<Item> findItemById(Long id);

    List<Item> findAllItemsById(Long id);

    List<Item> findAllItemsByName(String name);

    List<Item> findAllItemsByAmountAvailable(Long amountAvailable);

    List<Item> findAllItemsByPrice(Integer price);

    List<Item> findAllItemsByColor(String color);

    List<Item> findAllItemsByRefurbished(Boolean refurbished);

    void deleteAllItemsByName(String name);

    void deleteAllItemsByAmountAvailable(Long amountAvailable);

    void deleteAllItemsByPrice(Integer price);

    void deleteAllItemsByColor(String color);

    void deleteAllItemsByRefurbished(Boolean refurbished);

    void deleteAllItems();
}
