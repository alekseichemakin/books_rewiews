package ru.lexa.books_reviews.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import ru.lexa.books_reviews.controller.dto.author.AuthorFilterDTO;
import ru.lexa.books_reviews.domain.AuthorDomain;
import ru.lexa.books_reviews.repository.AuthorRepository;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.mapper.AuthorDomainMapper;
import ru.lexa.books_reviews.repository.mapper.AuthorDomainMapperImpl;
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
		when(authorRepository.save(Mockito.any(Author.class))).thenAnswer(i -> i.getArguments()[0]);
		saveAuthor.setName("test");

		AuthorDomain author = authorService.create(saveAuthor);
		assertEquals("test", author.getName());
	}

	@Test
	public void whenReadAuthor_ReturnAuthor() {
		Author saveAuthor = new Author();
		saveAuthor.setId(1L);
		saveAuthor.setName("test");
		when(authorRepository.findById(1L)).thenReturn(Optional.of(saveAuthor));


		AuthorDomain author = authorService.read(1);
		assertEquals("test", author.getName());
	}

	@Test
	public void whenReadBooks_ReturnBooks() {
		List<Author> authors = new ArrayList<>();
		Author saveAuthor1 = new Author();
		Author saveAuthor2 = new Author();
		Author saveAuthor3 = new Author();

		authors.add(saveAuthor1);
		authors.add(saveAuthor2);
		authors.add(saveAuthor3);
		when(authorRepository.findAll()).thenReturn(authors);

		List<AuthorDomain> authorsResponse = authorService.readAll(new AuthorFilterDTO());
		assertEquals(3, authorsResponse.size());
	}
}
