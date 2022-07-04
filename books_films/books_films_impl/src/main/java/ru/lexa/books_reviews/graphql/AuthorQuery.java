package ru.lexa.books_reviews.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import controller.dto.author.AuthorFilterDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lexa.books_reviews.domain.AuthorDomain;
import ru.lexa.books_reviews.service.AuthorService;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class AuthorQuery implements GraphQLQueryResolver {

    private AuthorService authorService;

    public List<AuthorDomain> getAuthors(final String authorName, final String bookName, final String filmName) {
        return authorService.readAll(new AuthorFilterDTO(authorName, bookName, filmName, null, null, null));
    }
    public Optional<AuthorDomain> getAuthor(final long id) {
        return Optional.ofNullable(authorService.read(id));
    }
}
