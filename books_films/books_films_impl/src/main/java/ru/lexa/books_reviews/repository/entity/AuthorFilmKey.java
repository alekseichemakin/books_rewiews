package ru.lexa.books_reviews.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorFilmKey implements Serializable {
    @Column(name = "author_id")
    Long authorId;

    @Column(name = "film_id")
    Long filmId;
}
