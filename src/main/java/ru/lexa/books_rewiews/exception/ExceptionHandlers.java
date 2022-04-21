package ru.lexa.books_rewiews.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<?> errorAuthentication() {
//        return ResponseEntity.badRequest().body("ошибка при вводе: неверное имя");
//    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<?> bookNotFound() {
        return ResponseEntity.badRequest().body("ошибка при вводе: нет книги с данным id");
    }
}