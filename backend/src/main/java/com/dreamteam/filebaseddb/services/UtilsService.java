package com.dreamteam.filebaseddb.services;

import com.dreamteam.filebaseddb.entities.Item;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UtilsService {
    private static final String databaseName = "D:\\Programs\\IntelliJIDEA\\filebased-db\\backend\\src\\main\\resources\\db\\%s.csv";

    private final ItemService itemService;

    public File getBackupFile() {
        return new File(databaseName.formatted(System.getProperty("databaseName")));
    }

    public File getBackupFileXLSX() {
        try {
            Workbook workbook = new SXSSFWorkbook();
            Sheet sheet = workbook.createSheet("items");

            Row heading = sheet.createRow(0);
            heading.createCell(0).setCellValue("ID");
            heading.createCell(1).setCellValue("Name");
            heading.createCell(2).setCellValue("Amount available");
            heading.createCell(3).setCellValue("Price");
            heading.createCell(4).setCellValue("Color");
            heading.createCell(5).setCellValue("Refurbished");

            List<Item> items = itemService.getAllItems();

            for (int i = 0; i < items.size(); i++) {
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(items.get(i).getId());
                row.createCell(1).setCellValue(items.get(i).getName());
                row.createCell(2).setCellValue(items.get(i).getAmountAvailable());
                row.createCell(3).setCellValue(items.get(i).getPrice());
                row.createCell(4).setCellValue(items.get(i).getColor());
                row.createCell(5).setCellValue(items.get(i).getRefurbished());
            }

            File xlsxFile = File.createTempFile("filebased-db", ".xlsx");
            FileOutputStream outputStream = new FileOutputStream(xlsxFile);

            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
            return xlsxFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
