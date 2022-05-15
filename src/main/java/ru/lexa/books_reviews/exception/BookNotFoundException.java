package ru.lexa.books_reviews.exception;

public class BookNotFoundException extends InputErrorException {
	public BookNotFoundException() {
		super("Нет книги с данным id");
	}
}
