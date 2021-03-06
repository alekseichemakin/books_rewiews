package ru.lexa.books_reviews.reviews.exception;

/**
 *  Исключения вознокающие, когда не найден отзыв с переданным Id
 */
public class ReviewNotFoundException extends InputErrorException{
	public ReviewNotFoundException(long id) {
		super("Нет отзыва с данным id: " + id);
	}
}
