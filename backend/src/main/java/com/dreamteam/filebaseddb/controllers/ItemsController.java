package com.dreamteam.filebaseddb.controllers;

import com.dreamteam.filebaseddb.entities.Item;
import com.dreamteam.filebaseddb.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/items")
@CrossOrigin(origins = "http://localhost:8081")
@RequiredArgsConstructor
public class ItemsController {
    private final ItemService itemService;

    @GetMapping()
    public @ResponseBody ResponseEntity<List<Item>> getAllItems(@RequestParam String databaseName) {
        System.setProperty("databaseName", databaseName);
        return ResponseEntity.ok().body(itemService.getAllItems());
    }

    @PostMapping("/add")
    public @ResponseBody void addItem(@RequestBody Item item, @RequestParam String databaseName) {
        System.setProperty("databaseName", databaseName);
        itemService.addItem(item);
    }

    @PutMapping("/edit")
    public @ResponseBody void editItem(@RequestBody Item item, @RequestParam String databaseName, @RequestParam Long oldItemId) {
        System.setProperty("databaseName", databaseName);
        itemService.editItem(oldItemId, item);
    }

    @DeleteMapping("/delete")
    public @ResponseBody void deleteItem(@RequestParam Long id, @RequestParam String databaseName) {
        System.setProperty("databaseName", databaseName);
        itemService.deleteItem(id);
    }

    @GetMapping("/search")
    public @ResponseBody ResponseEntity<List<Item>> searchItems(@RequestParam String searchQuery, @RequestParam String databaseName) {
        System.setProperty("databaseName", databaseName);
        return ResponseEntity.ok().body(itemService.searchItems(searchQuery));
    }

    @DeleteMapping("/deleteItems")
    public @ResponseBody void deleteItems(@RequestParam String searchQuery, @RequestParam String databaseName) {
        System.setProperty("databaseName", databaseName);
        itemService.deleteItemsBySearchQuery(searchQuery);
    }
}
