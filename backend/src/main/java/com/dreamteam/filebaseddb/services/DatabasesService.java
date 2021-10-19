package com.dreamteam.filebaseddb.services;

import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DatabasesService {
    private static final String storage = "D:\\Programs\\IntelliJIDEA\\filebased-db\\backend\\src\\main\\resources\\db";

    public List<String> getAllDatabases() {
        try {
            return Files.walk(Path.of(storage))
                    .filter(Files::isRegularFile) // only files
                    .map(Path::getFileName) // short filenames
                    .map(Path::toString) // filename -> string
                    .map(FileNameUtils::getBaseName) // reducing extension
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
