package ru.lexa.books_reviews.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.jpa.domain.Specification;
import ru.lexa.books_reviews.controller.dto.author.AuthorFilterDTO;
import ru.lexa.books_reviews.domain.AuthorDomain;
import ru.lexa.books_reviews.repository.AuthorRepository;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.mapper.AuthorDomainMapper;
import ru.lexa.books_reviews.service.implementation.AuthorServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthorServiceTest {
	@Mock
	AuthorRepository authorRepository;

	@Mock
	AuthorDomainMapper authorDomainMapper;

	@InjectMocks
	AuthorServiceImpl authorService;


	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void whenSaveAuthor_ReturnAuthor() {
		AuthorDomain saveAuthor = new AuthorDomain();
		saveAuthor.setName("test");
		when(authorDomainMapper.authorToDomain(Mockito.any(Author.class))).thenReturn(saveAuthor);
		when(authorDomainMapper.domainToAuthor(Mockito.any(AuthorDomain.class))).thenReturn(new Author());
		when(authorRepository.save(Mockito.any(Author.class))).thenAnswer(i -> i.getArguments()[0]);

		AuthorDomain author = authorService.create(saveAuthor);
		assertEquals("test", author.getName());
	}

	@Test
	public void whenReadAuthor_ReturnAuthor() {
		AuthorDomain saveAuthor = new AuthorDomain();
		saveAuthor.setId(1L);
		saveAuthor.setName("test");
		Author author = new Author();
		author.setBooks(new ArrayList<>());
		author.setFilms(new ArrayList<>());
		when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
		when(authorDomainMapper.authorToDomain(Mockito.any(Author.class))).thenReturn(saveAuthor);
		when(authorDomainMapper.domainToAuthor(Mockito.any(AuthorDomain.class))).thenReturn(new Author());

		AuthorDomain authorRet = authorService.read(1);
		assertEquals("test", authorRet.getName());
	}

	@Test
	public void whenReadAuthors_ReturnAuthors() {
		List<Author> authors = new ArrayList<>();
		Author saveAuthor1 = new Author();
		Author saveAuthor2 = new Author();
		Author saveAuthor3 = new Author();

		authors.add(saveAuthor1);
		authors.add(saveAuthor2);
		authors.add(saveAuthor3);

		AuthorDomain authorDomain = new AuthorDomain();
		authorDomain.setFilms(new ArrayList<>());
		authorDomain.setBooks(new ArrayList<>());
		when(authorRepository.findAll(Mockito.any(Specification.class))).thenReturn(authors);
		when(authorDomainMapper.authorToDomain(Mockito.any(Author.class))).thenReturn(authorDomain);

		List<AuthorDomain> authorsResponse = authorService.readAll(new AuthorFilterDTO());
		assertEquals(3, authorsResponse.size());
	}
}
