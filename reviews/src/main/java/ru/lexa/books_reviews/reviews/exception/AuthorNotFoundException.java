package ru.lexa.books_reviews.reviews.exception;

/**
 *  Исключения вознокающие когда не найден автор с данным Id
 */
public class AuthorNotFoundException extends InputErrorException {
	public AuthorNotFoundException(long id) {
		super("Нет автора с данным id: " + id);
	}
}
