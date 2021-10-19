package com.dreamteam.filebaseddb.exceptions;

public class IllegalItemFormatException extends IllegalStateException {
    private static final String ILLEGAL_ITEM = "Wrong item format";

    public IllegalItemFormatException() {
        super(ILLEGAL_ITEM);
    }

    public IllegalItemFormatException(String msg) {
        super("[%s] %s".formatted(ILLEGAL_ITEM, msg));
    }

    public IllegalItemFormatException(String msg, Throwable cause) {
        super("[%s] %s".formatted(ILLEGAL_ITEM, msg), cause);
    }
}