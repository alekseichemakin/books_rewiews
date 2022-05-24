package ru.lexa.books_reviews.domain;

import lombok.Data;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;

import java.util.Collection;
import java.util.List;

@Data
public class AuthorDomain {

	private long id;

	private String name;

	private Collection<Book> books;

	private Collection<Film> films;

	private List<Long> bookIds;

	private List<Long> filmIds;
}
