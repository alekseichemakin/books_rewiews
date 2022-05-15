package ru.lexa.books_reviews.exception;

public class AuthorNotFoundException extends InputErrorException{
	public AuthorNotFoundException() {
		super("Нет автора с данным id");
	}
}
