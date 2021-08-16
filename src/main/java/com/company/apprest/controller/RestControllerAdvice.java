package com.company.apprest.controller;

import com.company.apprest.entity.response.ErrorMessage;
import com.company.apprest.exception.UsuarioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.MethodNotAllowedException;

import javax.persistence.EntityNotFoundException;
@ControllerAdvice
public class RestControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(Exception exception){
        String message = "Entidade não encontrada!!!";
        return buildErrorMessageResponseEntity(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception exception){
        String message = "Internal Server Error";
        return buildErrorMessageResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsuarioException.class)
    public ResponseEntity<ErrorMessage> handleUsuarioException(Exception exception){
        String message = exception.getMessage();
        return buildErrorMessageResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(Exception exception){
        String message = exception.getLocalizedMessage();
        return buildErrorMessageResponseEntity(message, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<ErrorMessage> handleMethodNotAllowed(Exception exception){
        String message = exception.getMessage();
        return buildErrorMessageResponseEntity(message, HttpStatus.METHOD_NOT_ALLOWED);
    }

    private ResponseEntity<ErrorMessage> buildErrorMessageResponseEntity(String message, HttpStatus httpStatus){

        return new ResponseEntity<>( ErrorMessage.builder()
                                                .message(message)
                                                .code(httpStatus.value())
                                                .build(),  httpStatus);

    }


}
