package ru.lexa.books_reviews.repository.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
