package com.dreamteam.filebaseddb.controllers;

import com.dreamteam.filebaseddb.services.DatabasesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/databases")
@CrossOrigin(origins = "http://localhost:8081")
@RequiredArgsConstructor
public class DatabasesController {
    private final DatabasesService databasesService;

    @GetMapping()
    public @ResponseBody ResponseEntity<List<String>> getAllDatabases() {
        return ResponseEntity.ok().body(databasesService.getAllDatabases());
    }

    @PostMapping("/switchTo")
    public @ResponseBody void switchToDatabase(@RequestParam String databaseName) {
        System.setProperty("databaseName", databaseName);
    }

    @PostMapping("/add")
    public @ResponseBody boolean addDatabase(@RequestParam String databaseName) throws IOException {
        System.setProperty("databaseName", databaseName);
        File fileForSaving = new File("D:\\Programs\\IntelliJIDEA\\filebased-db\\backend\\src\\main\\resources\\db\\%s.csv".formatted(databaseName));
        return fileForSaving.createNewFile();
    }

    @PostMapping("/remove")
    public @ResponseBody boolean removeDatabase(@RequestParam String databaseName) {
        File fileForDeleting = new File("D:\\Programs\\IntelliJIDEA\\filebased-db\\backend\\src\\main\\resources\\db\\%s.csv".formatted(databaseName));
        return fileForDeleting.delete();
    }
}
