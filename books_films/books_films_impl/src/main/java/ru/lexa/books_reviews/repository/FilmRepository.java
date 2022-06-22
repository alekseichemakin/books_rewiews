package ru.lexa.books_reviews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lexa.books_reviews.repository.entity.Film;

import java.util.List;

/**
 * Класс работающий с таблицей film
 */
public interface FilmRepository extends JpaRepository<Film, Long> {
	/**
	 * Возвращает список всех имеющихся фильмов
	 *
	 * @return список фильмов
	 */
	@Override
	List<Film> findAll();
}
