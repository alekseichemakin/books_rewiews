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
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

//	/**
//	 * Автор экранизированной книги
//	 */
//	@NotAudited
//	@OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
//	@ToString.Exclude
//	private List<AuthorFilm> authors = new ArrayList<>();

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

//	public Collection<AuthorFilm> getAuthorFilm() {
//		return authors;
//	}
//
//	public List<Author> getAuthors() {
//		return authors.stream().map(AuthorFilm::getAuthor).collect(Collectors.toList());
//	}
//
//	public void setAuthors(List<Author> authors) {
//		List<AuthorFilm> authorFilms = new ArrayList<>();
//		authors.forEach(author -> {
//			AuthorFilm authorFilm = new AuthorFilm(author, this);
//			authorFilms.add(authorFilm);
//			author.addFilm(this);
//		});
//		this.authors = authorFilms;
//	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Film film = (Film) o;
		return id != null && Objects.equals(id, film.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
