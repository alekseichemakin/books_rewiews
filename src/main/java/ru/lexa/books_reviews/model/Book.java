package ru.lexa.books_reviews.model;

import lombok.Data;
import org.hibernate.envers.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Entity
@Data
@Audited
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Review> review;

    @Column(unique = true, nullable = false)
    @NotEmpty(message = "Имя не должно быть пустым")
    private String name;
    private String description;
    @NotEmpty(message = "Автор не должен быть пустым")
    private String author;
}
