package ru.lexa.books_reviews.exception;

public class FilmNotFoundException extends InputErrorException{
	public FilmNotFoundException() {
		super("Нет фильиа с данным id");
	}
}
