package ru.lexa.books_reviews.reviews.exception;

/**
 *  Исключения вознокающие, когда не найдена книга с переданным Id
 */
public class BookNotFoundException extends InputErrorException {
	public BookNotFoundException(long id) {
		super("Нет книги с данным id: " + id);
	}
}
