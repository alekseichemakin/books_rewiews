package ru.lexa.books_reviews.repository.entity;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Data
@Audited
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	private String text;

	@Min(1)
	@Max(10)
	private int rating;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "book_id", nullable = false)
	private Book book;
}