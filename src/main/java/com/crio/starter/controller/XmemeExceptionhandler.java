package com.crio.starter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class XmemeExceptionhandler {
    @ExceptionHandler(value = XmemeNotFoundException.class)
    public ResponseEntity<Object> exception(XmemeNotFoundException exception) {
        return new ResponseEntity<>("Meme not Found", HttpStatus.NOT_FOUND);
    }
}
