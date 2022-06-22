package ru.lexa.books_reviews.exception;

/**
 *  Исключения вознокающие при неправильном вводе данных
 */
public class InputErrorException extends LexaException {
	public InputErrorException(String message) {
		super(message);
	}
}
