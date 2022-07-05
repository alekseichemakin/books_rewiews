package ru.lexa.books_reviews.repository.entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;


/**
 * Сущность автора
 */
@NamedEntityGraph(
		name = "author-entity-graph",
		attributeNodes = {
				@NamedAttributeNode(value = "books", subgraph = "books-subgraph"),
		},
		subgraphs = {
				@NamedSubgraph(
						name = "books-subgraph",
						attributeNodes = {
								@NamedAttributeNode("book")
						}
				)
		}
)
@Entity
@Data
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
		book.getAuthors().add(authorBook);
	}

	public void setBooks(List<Book> books) {
		List<AuthorBook> authorBooks = new ArrayList<>();
		books.forEach(book -> {
			AuthorBook authorBook = new AuthorBook(this, book);
			authorBooks.add(authorBook);
			book.getAuthors().add(authorBook);
		});
		this.books = authorBooks;
	}
}
