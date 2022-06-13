package ru.lexa.books_reviews.service;

import ru.lexa.books_reviews.controller.dto.author.AuthorFilterDTO;
import ru.lexa.books_reviews.domain.AuthorDomain;
import ru.lexa.books_reviews.exception.AuthorNotFoundException;
import ru.lexa.books_reviews.exception.NameErrorException;

import java.util.List;

/**
 * Сервис обрабатывающий запорсы с контроллера {@link ru.lexa.books_reviews.controller.AuthorController}
 */
public interface AuthorService {
	/**
	 * Создает автора с заданными параметрми
	 *
	 * @param author - автор для создания
	 * @return созданного автора
	 * @throws NameErrorException, если автор с данным именнем существует
	 */
	AuthorDomain create(AuthorDomain author);

	/**
	 * Возвращает автора по ID
	 *
	 * @param id - ID книги
	 * @return - объект автора с заданным ID
	 * @throws AuthorNotFoundException, если нет автора с данным ID
	 */
	AuthorDomain read(long id);

	/**
	 * Обновляет автора с заданным ID,
	 * в соответствии с переданным автором
	 *
	 * @param author - автор в соответсвии с которой нужно обновить данные
	 * @return - объект обновленного автора
	 * @throws AuthorNotFoundException, NameErrorException если нет книги с данным ID или книга с данным именем уже существует
	 */
	AuthorDomain update(AuthorDomain author);

	/**
	 * Удаляет автора с заданным ID
	 *
	 * @param id - id автора, которого нужно удалить
	 * @throws AuthorNotFoundException, если нет автора с данным ID
	 */
	void delete(long id);

	/**
	 * Возвращает список всех имеющихся авторов
	 *
	 * @return список авторов
	 */
	List<AuthorDomain> readAll(AuthorFilterDTO filter);

	/**
	 * Возвращает средний рейтинг автора
	 *
	 * @param id - id автора
	 * @throws AuthorNotFoundException, если нет автора с данным ID
	 */
	Double getAverageRating(long id);
}
