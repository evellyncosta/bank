package com.pismo.bank.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(Exception e){
        DefaultError erro = new DefaultError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
                return new ResponseEntity(erro, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity handleBusinessException(Exception e){
        DefaultError erro = new DefaultError(HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage());
        return new ResponseEntity(erro, HttpStatus.NOT_ACCEPTABLE);
    }
}
