package ru.lexa.books_reviews.exception;

public class NameErrorException extends InputErrorException{
	public NameErrorException() {
		super("Неверное имя");
	}
}
