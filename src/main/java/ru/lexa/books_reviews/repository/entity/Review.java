package ru.lexa.books_reviews.repository.entity;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Сущность отзыва
 */
@Entity
@Data
@Audited
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * Текст отзыва
	 */
	private String text;

	/**
	 * Рейтинг
	 */
	@Min(1)
	@Max(10)
	private int rating;

	/**
	 * Книга к которой принадледит отзыв
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id")
	private Book book;

	/**
	 * Фильм к которой принадледит отзыв
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "film_id")
	private Film film;
}