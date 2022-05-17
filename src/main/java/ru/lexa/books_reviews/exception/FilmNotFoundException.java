package ru.lexa.books_reviews.exception;

/**
 *  Исключения вознокающие, когда не найден фильм с переданным Id
 */
public class FilmNotFoundException extends InputErrorException{
	public FilmNotFoundException() {
		super("Нет фильиа с данным id");
	}
}
