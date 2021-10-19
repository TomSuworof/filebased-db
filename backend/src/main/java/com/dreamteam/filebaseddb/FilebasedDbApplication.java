package com.dreamteam.filebaseddb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilebasedDbApplication {

    public static void main(String[] args) {
        System.setProperty("databaseEnv", "database"); // default database
        SpringApplication.run(FilebasedDbApplication.class, args);
    }

}
