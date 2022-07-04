package ru.lexa.books_reviews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.lexa.books_reviews.repository.entity.Book;

import java.util.List;

/**
 * Класс работающий с таблицей book
 */
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

	/**
	 * Возвращает список всех имеющихся книг
	 *
	 * @return список книг
	 */
	@Override
	List<Book> findAll();
}