package ru.lexa.books_reviews.repository.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Сущность книги
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Audited
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * Название книги
	 */
	@Column(unique = true, nullable = false)
	@NotEmpty(message = "Имя не должно быть пустым")
	private String name;

	/**
	 * Описание книги
	 */
	private String description;

	/**
	 * Автор книги
	 */
	@NotAudited
	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
	private List<AuthorBook> authors = new ArrayList<>();

	/**
	 * Экранизации книги
	 */
	@OneToMany(mappedBy = "book", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
	private Collection<Film> films;

	public List<Author> getAuthors() {
		return authors.stream().map(AuthorBook::getAuthor).collect(Collectors.toList());
	}

	public void setAuthors(List<Author> authors) {
		List<AuthorBook> authorBooks = new ArrayList<>();
		authors.forEach(author -> {
			AuthorBook authorBook = new AuthorBook(author, this);
			authorBooks.add(authorBook);
			author.addBook(this);
		});
		this.authors = authorBooks;
	}

	public void addAuthor(Author author) {
		authors.add(new AuthorBook(author, this));
	}

	public void deleteAuthor(Author author) {
		authors.remove(new AuthorBook(author, this));
	}

	public List<AuthorBook> getAuthorBook() {
		return authors;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Book book = (Book) o;
		return id != null && Objects.equals(id, book.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
