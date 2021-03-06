package ru.lexa.books_reviews.service;


import controller.dto.book.BookFilterDTO;
import ru.lexa.books_reviews.controller.dto.review.BookReviewRequestDTO;
import ru.lexa.books_reviews.domain.BookDomain;
import ru.lexa.books_reviews.exception.BookNotFoundException;
import ru.lexa.books_reviews.exception.NameErrorException;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Film;

import java.util.List;

/**
 * Сервис обрабатывающий запорсы с контроллера {@link ru.lexa.books_reviews.controller.BookController}
 */
public interface BookService {
	/**
	 * Создает книгу с заданными параметрми
	 *
	 * @param book - книга для создания
	 * @return созданную книгу
	 * @throws NameErrorException, если книга с данным именнем существует
	 */
	BookDomain create(BookDomain book, BookReviewRequestDTO reviewRequestDTO);

	/**
	 * Возвращает книгу по ID
	 *
	 * @param id - ID книги
	 * @return - объект книги с заданным ID
	 * @throws BookNotFoundException, если нет книги с данным ID
	 */
	BookDomain read(long id);

	/**
	 * Обновляет книгу с заданным ID,
	 * в соответствии с переданной книгой
	 *
	 * @param book - книга в соответсвии с которой нужно обновить данные
	 * @return - объект обновленной книги
	 * @throws BookNotFoundException,NameErrorException если нет книги с данным ID или книга с данным именем уже существует
	 */
	BookDomain update(BookDomain book);

	/**
	 * Удаляет книгу с заданным ID
	 *
	 * @param id - id книги, которого нужно удалить
	 * @throws BookNotFoundException, если нет книги с данным ID
	 */
	void delete(long id);

	/**
	 * Вощвращает средний рейтинг отзвов по книги с переданным ID
	 *
	 * @param id - id книги
	 * @return - средний рейтинг
	 * @throws BookNotFoundException, если нет книги с данным ID
	 */
	double averageRating(long id);

	/**
	 * Возвращает список всех имеющихся книг
	 *
	 * @return список книг
	 */
	List<BookDomain> readAll(BookFilterDTO filter);

	/**
	 * Очищает кэш книги
	 *
	 * @param id - id книги
	 */
	void clearCache(long id);
}
