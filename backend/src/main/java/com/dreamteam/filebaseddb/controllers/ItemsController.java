package com.dreamteam.filebaseddb.controllers;

import com.dreamteam.filebaseddb.entities.Item;
import com.dreamteam.filebaseddb.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public @ResponseBody void addItem(@Valid @RequestBody Item item, @RequestParam String databaseName) {
        System.setProperty("databaseName", databaseName);
        long start = System.nanoTime();
        itemService.addItem(item);
        System.out.printf("Adding time: %d ns \n", System.nanoTime() - start);
    }

    @PutMapping("/edit")
    public @ResponseBody void editItem(@Valid @RequestBody Item item, @RequestParam String databaseName, @RequestParam Long oldItemId) {
        System.setProperty("databaseName", databaseName);
        long start = System.nanoTime();
        itemService.editItem(oldItemId, item);
        System.out.printf("Editing time: %d ns \n", System.nanoTime() - start);
    }

    @DeleteMapping("/delete")
    public @ResponseBody void deleteItem(@RequestParam Long id, @RequestParam String databaseName) {
        System.setProperty("databaseName", databaseName);
        long start = System.nanoTime();
        itemService.deleteItem(id);
        System.out.printf("Deleting time: %d ns \n", System.nanoTime() - start);
    }

    @GetMapping("/search")
    public @ResponseBody ResponseEntity<List<Item>> searchItems(@RequestParam String searchQuery, @RequestParam String databaseName) {
        System.setProperty("databaseName", databaseName);
        long start = System.nanoTime();
        ResponseEntity<List<Item>> response = ResponseEntity.ok().body(itemService.searchItems(searchQuery));
        System.out.printf("Search time: %d ns \n", System.nanoTime() - start);
        return response;
    }

    @DeleteMapping("/deleteItems")
    public @ResponseBody void deleteItems(@RequestParam String searchQuery, @RequestParam String databaseName) {
        System.setProperty("databaseName", databaseName);
        long start = System.nanoTime();
        itemService.deleteItemsBySearchQuery(searchQuery);
        System.out.printf("Deleting by search time: %d ns \n", System.nanoTime() - start);
    }
}
