package ru.lexa.books_reviews.exception;

/**
 *  Исключения вознокающие, когда не найден отзыв с переданным Id
 */
public class ReviewNotFoundException extends InputErrorException{
	public ReviewNotFoundException() {
		super("Нет отзыва с данным id.");
	}
}
