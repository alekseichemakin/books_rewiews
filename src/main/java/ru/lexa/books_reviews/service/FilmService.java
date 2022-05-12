package ru.lexa.books_reviews.service;

import ru.lexa.books_reviews.exception.InputErrorException;
import ru.lexa.books_reviews.repository.entity.Film;

import java.util.List;

public interface FilmService {
	/**
	 * Создает книгу с заданными параметрми
	 * @param book - книга для создания
	 * @return созданную книгу
	 * @throws InputErrorException, если книга с данным именнем существует
	 */
	Film create(Film film);

	/**
	 * Возвращает книгу по ID
	 * @param id - ID книги
	 * @return - объект книги с заданным ID
	 * @throws InputErrorException, если нет книги с данным ID
	 */
	Film read(long id);

	/**
	 * Обновляет книгу с заданным ID,
	 * в соответствии с переданной книгой
	 * @param book - книга в соответсвии с которой нужно обновить данные
	 * @return - объект обновленной книги
	 * @throws InputErrorException, если нет книги с данным ID или книга с данным именем уже существует
	 */
	Film update(Film film);

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
	List<Film> readAll();

}
