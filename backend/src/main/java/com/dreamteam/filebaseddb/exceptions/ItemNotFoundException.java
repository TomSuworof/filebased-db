package com.dreamteam.filebaseddb.exceptions;

public class ItemNotFoundException extends IllegalStateException {
    private static final String ITEM_NOT_FOUND = "Such item does not exist";

    public ItemNotFoundException() {
        super(ITEM_NOT_FOUND);
    }

    public ItemNotFoundException(String msg) {
        super("[%s] %s".formatted(ITEM_NOT_FOUND, msg));
    }

    public ItemNotFoundException(String msg, Throwable cause) {
        super("[%s] %s".formatted(ITEM_NOT_FOUND, msg), cause);
    }
}