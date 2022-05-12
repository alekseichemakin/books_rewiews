package ru.lexa.books_reviews.service;

import ru.lexa.books_reviews.exception.InputErrorException;
import ru.lexa.books_reviews.repository.entity.Author;

import java.util.List;

public interface AuthorService {
	/**
	 * Создает книгу с заданными параметрми
	 * @param book - книга для создания
	 * @return созданную книгу
	 * @throws InputErrorException, если книга с данным именнем существует
	 */
	Author create(Author author);

	/**
	 * Возвращает книгу по ID
	 * @param id - ID книги
	 * @return - объект книги с заданным ID
	 * @throws InputErrorException, если нет книги с данным ID
	 */
	Author read(long id);

	/**
	 * Обновляет книгу с заданным ID,
	 * в соответствии с переданной книгой
	 * @param book - книга в соответсвии с которой нужно обновить данные
	 * @return - объект обновленной книги
	 * @throws InputErrorException, если нет книги с данным ID или книга с данным именем уже существует
	 */
	Author update(Author author);

	/**
	 * Удаляет книгу с заданным ID
	 * @param id - id книги, которого нужно удалить
	 * @throws InputErrorException, если нет книги с данным ID
	 */
	void delete(long id);

	/**
	 * Возвращает список всех имеющихся книг
	 *
	 * @return список книг
	 */
	List<Author> readAll();
}
