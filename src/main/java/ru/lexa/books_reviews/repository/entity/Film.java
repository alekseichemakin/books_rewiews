package ru.lexa.books_reviews.repository.entity;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;
import java.util.Collection;

/**
 * Сущность фильма
 */
@Entity
@Data
@Audited
public class Film {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * Название фильма
	 */
	@Column(unique = true, nullable = false)
	@NotEmpty(message = "Имя не должно быть пустым")
	private String name;

	/**
	 * Автор экранизированной книги
	 */
	@ManyToOne
	@JoinColumn(name = "author_id")
	private Author author;

	/**
	 * Список отзывов к фильму
	 */
	@OneToMany(mappedBy = "film", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Review> review;

	/**
	 * Дата экранизации
	 */
	private Date dateRelease;

	/**
	 * Экранизированная книга
	 */
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;
}
