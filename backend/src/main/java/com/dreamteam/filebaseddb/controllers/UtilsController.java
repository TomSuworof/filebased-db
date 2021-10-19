package com.dreamteam.filebaseddb.controllers;

import com.dreamteam.filebaseddb.services.UtilsService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/api/utils")
@CrossOrigin(origins = "http://localhost:8081")
@RequiredArgsConstructor
public class UtilsController {
    private final UtilsService utilsService;

    @PostMapping("/backup")
    public @ResponseBody FileSystemResource getBackupFile(HttpServletResponse response, @RequestParam String databaseName) {
        System.setProperty("databaseName", databaseName);
        File backupFile = utilsService.getBackupFile();

        response.setHeader("Content-Disposition", "inline; filename=" + backupFile.getName());
        response.setHeader("Content-Length", String.valueOf(backupFile.length()));

        return new FileSystemResource(backupFile);
    }

    @PostMapping("/backupXLSX")
    public @ResponseBody FileSystemResource getBackupFileXLSX(HttpServletResponse response, @RequestParam String databaseName) {
        System.setProperty("databaseName", databaseName);
        File backupFile = utilsService.getBackupFileXLSX();

        response.setHeader("Content-Disposition", "inline; filename=" + backupFile.getName());
        response.setHeader("Content-Length", String.valueOf(backupFile.length()));

        return new FileSystemResource(backupFile);
    }

    @PostMapping("/uploadBackupFile")
    public @ResponseBody void uploadBackupFile(@RequestParam MultipartFile file, @RequestParam String databaseName) {
        System.setProperty("databaseName", databaseName);
        File fileForSaving = new File("D:\\Programs\\IntelliJIDEA\\filebased-db\\backend\\src\\main\\resources\\db\\%s.csv".formatted(databaseName));

        try (OutputStream stream = new FileOutputStream(fileForSaving)) {
            stream.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
