package com.dreamteam.filebaseddb.controlleradvices;

import com.dreamteam.filebaseddb.exceptions.DuplicatedItemException;
import com.dreamteam.filebaseddb.exceptions.IllegalItemFormatException;
import com.dreamteam.filebaseddb.exceptions.ItemNotFoundException;
import com.dreamteam.filebaseddb.payload.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
public class ItemAdvice {

    @ExceptionHandler(DuplicatedItemException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedItem(DuplicatedItemException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ErrorResponse response = new ErrorResponse(new Date(), status.value(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleItemNotFound(ItemNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse response = new ErrorResponse(new Date(), status.value(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(IllegalItemFormatException.class)
    public ResponseEntity<ErrorResponse> handleIllegalItemFormat(IllegalItemFormatException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse response = new ErrorResponse(new Date(), status.value(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(response);
    }
}