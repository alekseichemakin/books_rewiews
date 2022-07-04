package ru.lexa.books_reviews.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lexa.books_reviews.domain.AuthorDomain;
import ru.lexa.books_reviews.service.AuthorService;

import java.util.Optional;

@Component
@AllArgsConstructor
public class AuthorMutation implements GraphQLMutationResolver {
    private AuthorService authorService;

    public Optional<AuthorDomain> createAuthor(final String name) {
        AuthorDomain authorDomain = new AuthorDomain();
        authorDomain.setName(name);
        return Optional.ofNullable(authorService.create(authorDomain));
    }

    public Optional<AuthorDomain> updateAuthor(final long id, final String name) {
        AuthorDomain authorDomain = new AuthorDomain();
        authorDomain.setName(name);
        authorDomain.setId(id);
        return Optional.ofNullable(authorService.update(authorDomain));
    }

    public boolean deleteAuthor(final long id) {
        authorService.delete(id);
        return true;
    }
}
