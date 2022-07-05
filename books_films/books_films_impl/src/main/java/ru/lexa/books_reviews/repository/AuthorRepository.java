package ru.lexa.books_reviews.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.lexa.books_reviews.repository.entity.Author;

import java.util.List;
import java.util.Optional;

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

    /**
     * Возвращает граф автора по id
     *
     * @return автор
     */
    @Override
    @EntityGraph(value = "author-entity-graph")
    Optional<Author> findById(Long id);
}
