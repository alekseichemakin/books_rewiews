package ru.lexa.books_reviews.repository;

import org.springframework.data.repository.CrudRepository;
import ru.lexa.books_reviews.repository.entity.AuthorBook;
import ru.lexa.books_reviews.repository.entity.AuthorBookKey;

public interface AuthorBookRepository extends CrudRepository<AuthorBook, AuthorBookKey> {
}
