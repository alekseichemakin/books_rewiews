package ru.lexa.books_reviews.repository.entity;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;


/**
 * Сущность автора
 */
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
	@ManyToMany(mappedBy = "authors")
	private Collection<Book> books;

	/**
	 * Фильмы экранизированные по книгам данного автора
	 */
	@ManyToMany(mappedBy = "authors")
	private Collection<Film> films;
}
