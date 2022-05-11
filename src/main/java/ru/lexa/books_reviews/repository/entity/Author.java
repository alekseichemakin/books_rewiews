package ru.lexa.books_reviews.repository.entity;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Audited
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Book> books;

	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Film> films;
}
