package ru.lexa.books_reviews.exception;

public class ReviewNotFoundException extends InputErrorException{
	public ReviewNotFoundException() {
		super("Нет отзыва с данным id.");
	}
}
