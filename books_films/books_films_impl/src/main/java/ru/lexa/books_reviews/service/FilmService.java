package ru.lexa.books_reviews.service;

import ru.lexa.books_reviews.domain.FilmDomain;
import ru.lexa.books_reviews.exception.FilmNotFoundException;
import ru.lexa.books_reviews.exception.NameErrorException;

import java.util.List;

/**
 * Сервис обрабатывающий запорсы с контроллера {@link ru.lexa.books_reviews.controller.FilmController}
 */
public interface FilmService {
	/**
	 * Создает фильм с заданными параметрми
	 *
	 * @param film - фильм для создания
	 * @return созданный фильм
	 * @throws NameErrorException, если фильм с данным именнем существует
	 */
	FilmDomain create(FilmDomain film);

	/**
	 * Возвращает фильм по ID
	 *
	 * @param id - ID фильма
	 * @return - объект фильма с заданным ID
	 * @throws FilmNotFoundException, если нет фильма с данным ID
	 */
	FilmDomain read(long id);

	/**
	 * Обновляет фильм с заданным ID,
	 * в соответствии с переданным фильмом
	 *
	 * @param film - фильм в соответсвии с которой нужно обновить данные
	 * @return - объект обновленного фильма
	 * @throws FilmNotFoundException, NameErrorException если нет фильма с данным ID или фильма с данным именем уже существует
	 */
	FilmDomain update(FilmDomain film);

	/**
	 * Удаляет фильм с заданным ID
	 *
	 * @param id - id фильма, которого нужно удалить
	 * @throws FilmNotFoundException, если нет фильма с данным ID
	 */
	void delete(long id);

	/**
	 * Возвращает список всех имеющихся фильмов
	 *
	 * @return список фильмов
	 */
	List<FilmDomain> readAll();

}
