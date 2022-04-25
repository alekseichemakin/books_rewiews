package ru.lexa.books_reviews.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> errorName() {
        return ResponseEntity.badRequest().body("ошибка при вводе: неверное имя");
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<?> bookNotFound() {
        return ResponseEntity.badRequest().body("ошибка при вводе: нет книги с данным id");
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<?> reviewNotFound() {
        return ResponseEntity.badRequest().body("ошибка при вводе: нет отзыва с данным id");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidateException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body("Ошибка валидации: " + e.getFieldError().getDefaultMessage());
    }
}