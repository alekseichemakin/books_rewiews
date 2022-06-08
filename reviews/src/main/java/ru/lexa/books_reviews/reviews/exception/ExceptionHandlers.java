package ru.lexa.books_reviews.reviews.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

/**
 *  Обработчик исключений
 */
@ControllerAdvice
public class ExceptionHandlers {

	@ExceptionHandler(InputErrorException.class)
	public ResponseEntity<?> inputError(InputErrorException e) {
		return ResponseEntity.badRequest().body("Ошибка при вводе: " + e.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidateException(MethodArgumentNotValidException e) {
		return ResponseEntity.badRequest().body("Ошибка валидации: " + e.getFieldError().getDefaultMessage());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleValidateException(ConstraintViolationException e) {
		return ResponseEntity.badRequest().body("Ошибка валидации: " + e.getConstraintViolations().iterator().next().getMessage());
	}
}