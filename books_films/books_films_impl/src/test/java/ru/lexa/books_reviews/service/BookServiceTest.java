package ru.lexa.books_reviews.service;

import controller.dto.book.BookFilterDTO;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.jpa.domain.Specification;
import ru.lexa.books_reviews.domain.AuthorDomain;
import ru.lexa.books_reviews.domain.BookDomain;
import ru.lexa.books_reviews.integration.service.ReviewsService;
import ru.lexa.books_reviews.repository.BookRepository;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.mapper.BookDomainMapper;
import ru.lexa.books_reviews.service.implementation.BookServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

	@Mock
	BookRepository bookRepository;

	@Mock
	BookDomainMapper bookDomainMapper;

	@Mock
	AuthorService authorService;

	@Mock
	ReviewsService reviewsService;

	@InjectMocks
	BookServiceImpl bookService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void whenSaveBook_ReturnBook() {
		BookDomain saveBook = new BookDomain();
		Author author = new Author();
		List<Author> authors = new ArrayList<>();
		when(bookDomainMapper.bookToDomain(Mockito.any(Book.class))).thenReturn(saveBook);
		when(bookDomainMapper.domainToBook(Mockito.any(BookDomain.class))).thenReturn(new Book());
		when(bookRepository.save(Mockito.any(Book.class))).thenAnswer(i -> i.getArguments()[0]);
		when(authorService.read(Mockito.anyLong())).thenReturn(new AuthorDomain());
		author.setName("test");
		author.setId(0L);
		saveBook.setName("test");
		authors.add(author);
		saveBook.setAuthors(authors);
		BookDomain book = bookService.create(saveBook, null);

		assertEquals("test", book.getName());
		assertEquals("test", book.getAuthors().stream().findFirst().get().getName());
	}

	@Test
	public void whenReadBook_ReturnBook() {
		long id = 1L;
		BookDomain saveBook = new BookDomain();
		Author author = new Author();
		List<Author> authors = new ArrayList<>();
		saveBook.setId(id);
		saveBook.setName("test");
		author.setName("test");
		authors.add(author);
		saveBook.setAuthors(authors);
		Book retBook = new Book();
		retBook.setId(1L);
		when(bookRepository.findById(1L)).thenReturn(Optional.of(retBook));
		when(bookDomainMapper.bookToDomain(Mockito.any(Book.class))).thenReturn(saveBook);
		BookDomain book = bookService.read(id);

		assertEquals("test", book.getName());
		assertEquals("test", book.getAuthors().stream().findFirst().get().getName());
	}

	@Test
	public void whenReadBooks_ReturnBooks() {
		List<Book> books = new ArrayList<>();
		Book saveBook1 = new Book();
		Book saveBook2 = new Book();
		Book saveBook3 = new Book();

		saveBook1.setId(1L);
		saveBook2.setId(2L);
		saveBook3.setId(3L);

		books.add(saveBook1);
		books.add(saveBook2);
		books.add(saveBook3);

		BookDomain bookDomain = new BookDomain();
		bookDomain.setAuthors(new ArrayList<>());
		when(bookRepository.findAll(Mockito.any(Specification.class))).thenReturn(books);
		when(bookDomainMapper.bookToDomain(Mockito.any(Book.class))).thenReturn(bookDomain);

		List<BookDomain> booksResponse = bookService.readAll(new BookFilterDTO());
		assertEquals(3, booksResponse.size());
	}

	@Test
	public void whenGetRating_ReturnRating() {
		when(reviewsService.getBookAverageRating(Mockito.anyLong())).thenReturn(0.0);
		double rating = bookService.averageRating(1);
		assertEquals(0, rating, 0.1);
	}
}
