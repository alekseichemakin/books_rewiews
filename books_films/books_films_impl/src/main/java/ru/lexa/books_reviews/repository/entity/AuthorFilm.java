package ru.lexa.books_reviews.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorFilm {
    @EmbeddedId
    AuthorFilmKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("authorId")
    @JoinColumn(name = "author_id")
    Author author;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("filmId")
    @JoinColumn(name = "film_id")
    Film film;

    public AuthorFilm(Author author, Film film) {
        this.author = author;
        this.film = film;
        this.id = new AuthorFilmKey(author.getId(), film.getId());
    }
}
