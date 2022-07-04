package ru.lexa.books_reviews.repository.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Сущность автора
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Audited
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * Имя автора
	 */
	@Column(unique = true, nullable = false)
	@NotEmpty(message = "Имя не должно быть пустым")
	private String name;

	/**
	 * Книги написанные автором
	 */
	@NotAudited
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<AuthorBook> books = new ArrayList<>();

	public void addBook(Book book) {
		AuthorBook authorBook = new AuthorBook(this, book);
		books.add(authorBook);
		book.getAuthorBook().add(authorBook);
	}

	public void removeBook(Book book) {
		for (Iterator<AuthorBook> iterator = books.iterator();
			 iterator.hasNext(); ) {
			AuthorBook authorBook = iterator.next();

			if (authorBook.getAuthor().equals(this) &&
					authorBook.getBook().equals(book)) {
				iterator.remove();
				authorBook.getBook().getAuthorBook().remove(authorBook);
				authorBook.setAuthor(null);
				authorBook.setBook(null);
			}
		}
	}

	public List<Book> getBooks() {
		return books.stream().map(AuthorBook::getBook).collect(Collectors.toList());
	}

	public void setBooks(List<Book> books) {
		List<AuthorBook> authorBooks = new ArrayList<>();
		books.forEach(book -> {
			AuthorBook authorBook = new AuthorBook(this, book);
			authorBooks.add(authorBook);
			book.getAuthorBook().add(authorBook);
		});
		this.books = authorBooks;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Author author = (Author) o;
		return id != null && Objects.equals(id, author.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
