package ru.lexa.books_reviews.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;

import java.util.List;

/**
 * Класс работающий с таблицей author
 */
public interface AuthorRepository extends CrudRepository<Author, Long>, JpaSpecificationExecutor<Author> {
	/**
	 * Возвращает список всех имеющихся авторов
	 *
	 * @return список авторов
	 */
	@Override
	List<Author> findAll();
}
