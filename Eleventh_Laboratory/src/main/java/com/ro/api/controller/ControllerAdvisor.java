package com.ro.api.controller;

import com.ro.api.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvisor {
    @ResponseBody
    @ExceptionHandler({
            PlayerNotFoundException.class,
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String itemNotFoundHandler(RuntimeException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler({
            InvalidGameDataException.class,
            InvalidGamePlayersException.class,
            InvalidMovesPatternException.class,
            OptimizationException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidDataHandler(RuntimeException ex) {
        return ex.getMessage();
    }
}
