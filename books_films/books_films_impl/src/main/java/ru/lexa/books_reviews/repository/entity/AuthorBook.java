package ru.lexa.books_reviews.repository.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AuthorBook {
    @EmbeddedId
    AuthorBookKey id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("authorId")
    @JoinColumn(name = "author_id")
    @ToString.Exclude
    Author author;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    Book book;

    public AuthorBook(Author author, Book book) {
        this.author = author;
        this.book = book;
        this.id = new AuthorBookKey(author.getId(), book.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AuthorBook that = (AuthorBook) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
