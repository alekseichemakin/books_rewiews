package ru.lexa.books_reviews.reviews.exception;

/**
 *  Исключения вознокающие при неправильном вводе имени
 */
public class NameErrorException extends InputErrorException{
	public NameErrorException() {
		super("Неверное имя");
	}
}
