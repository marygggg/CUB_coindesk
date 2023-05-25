package com.james.coindesk.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public String exception(Exception e) {
        e.printStackTrace();
        return "error";
    }
}