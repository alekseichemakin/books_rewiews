package ru.lexa.books_reviews.repository;

import org.springframework.data.repository.CrudRepository;
import ru.lexa.books_reviews.repository.entity.AuthorFilm;
import ru.lexa.books_reviews.repository.entity.AuthorFilmKey;

public interface AuthorFilmRepository extends CrudRepository<AuthorFilm, AuthorFilmKey> {
}
