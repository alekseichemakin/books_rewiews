package ru.lexa.books_reviews.reviews.exception;

/**
 *  Базовый класс исключений
 */
public class LexaException extends RuntimeException{
    public LexaException(String message) {
        super(message);
    }
}
