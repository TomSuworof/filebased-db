package com.dreamteam.filebaseddb.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.Arrays;

@Getter
@Setter
@ToString
public class Item {
    private Long id;
    private String name;
    private Long amountAvailable;
    private Integer price;
    private String color;
    private Boolean refurbished;

    public Item(Long id, String name, Long amountAvailable, Integer price, String color, Boolean refurbished) {
        this.id = id;
        this.name = name;
        this.amountAvailable = amountAvailable;
        this.price = price;
        this.color = color;
        this.refurbished = refurbished == null ? Boolean.FALSE : refurbished;
    }

    public boolean containsEmpty() throws IllegalAccessException {
        for (Field f : getClass().getDeclaredFields())
            if (f.get(this) == null) {
                return true;
            }
        return false;
    }

    public static Item fromCSVRow(String[] data) {
        return new Item(
                Long.parseLong(data[0]),
                data[1],
                Long.parseLong(data[2]),
                Integer.parseInt(data[3]),
                data[4],
                Boolean.parseBoolean(data[5]));
    }

    public String[] toCSVRow() {
        return new String[] {
                this.getId().toString(),
                this.getName(),
                this.getAmountAvailable().toString(),
                this.getPrice().toString(),
                this.getColor(),
                this.getRefurbished().toString()
        };
    }
}
