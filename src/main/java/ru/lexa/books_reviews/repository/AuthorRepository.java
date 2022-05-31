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

	@Query(value = "select avg(rat)\n" +
			"from (select a.id as id, avg(rating) as rat\n" +
			"      from author a\n" +
			"               inner join book_author ba on a.id = ba.author_id\n" +
			"               inner join book b on ba.book_id = b.id\n" +
			"               inner join review r on b.id = r.book_id\n" +
			"      group by a.id, b.id) as a group by a.id\n" +
			"having a.id= ?1", nativeQuery = true)
	Double getAverageRating(long id);
}
