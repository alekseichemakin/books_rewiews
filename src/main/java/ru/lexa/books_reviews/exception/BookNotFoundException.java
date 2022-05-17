package ru.lexa.books_reviews.exception;

/**
 *  Исключения вознокающие, когда не найдена книга с переданным Id
 */
public class BookNotFoundException extends InputErrorException {
	public BookNotFoundException() {
		super("Нет книги с данным id");
	}
}
