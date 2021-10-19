package com.dreamteam.filebaseddb.exceptions;

public class DuplicatedItemException extends IllegalStateException {
    private static final String ITEM_FOUND = "Such item exists";

    public DuplicatedItemException() {
        super(ITEM_FOUND);
    }

    public DuplicatedItemException(String msg) {
        super("[%s] %s".formatted(ITEM_FOUND, msg));
    }

    public DuplicatedItemException(String msg, Throwable cause) {
        super("[%s] %s".formatted(ITEM_FOUND, msg), cause);
    }
}
