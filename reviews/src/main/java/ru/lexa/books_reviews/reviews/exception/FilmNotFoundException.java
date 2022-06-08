package ru.lexa.books_reviews.reviews.exception;

/**
 *  Исключения вознокающие, когда не найден фильм с переданным Id
 */
public class FilmNotFoundException extends InputErrorException {
	public FilmNotFoundException(long id) {
		super("Нет фильиа с данным id: " + id);
	}
}
