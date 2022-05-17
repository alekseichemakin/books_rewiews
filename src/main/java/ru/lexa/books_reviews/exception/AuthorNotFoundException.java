package ru.lexa.books_reviews.exception;

/**
 *  Исключения вознокающие когда не найден автор с данным Id
 */
public class AuthorNotFoundException extends InputErrorException{
	public AuthorNotFoundException() {
		super("Нет автора с данным id");
	}
}
