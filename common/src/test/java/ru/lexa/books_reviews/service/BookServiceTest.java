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
import ru.lexa.books_reviews.controller.dto.book.BookFilterDTO;
import ru.lexa.books_reviews.domain.BookDomain;
import ru.lexa.books_reviews.repository.BookRepository;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.mapper.BookDomainMapper;
import ru.lexa.books_reviews.service.implementation.BookServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

	@Mock
	BookRepository bookRepository;

	@Mock
	BookDomainMapper bookDomainMapper;

	@Mock
	ReviewService reviewService;

	@InjectMocks
	BookServiceImpl bookService;


	@Before
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

		author.setName("test");
		saveBook.setName("test");
		authors.add(author);
		saveBook.setAuthors(authors);
		BookDomain book = bookService.create(saveBook);

		assertEquals("test", book.getName());
		assertEquals("test", book.getAuthors().stream().findFirst().get().getName());
	}

	@Test
	public void whenReadBook_ReturnBook() {
		BookDomain saveBook = new BookDomain();
		Author author = new Author();
		List<Author> authors = new ArrayList<>();
		saveBook.setId(1L);
		saveBook.setName("test");
		author.setName("test");
		authors.add(author);
		saveBook.setAuthors(authors);
		saveBook.setReviews(new ArrayList<>());
		when(bookRepository.findById(1L)).thenReturn(Optional.of(new Book()));
		when(bookDomainMapper.bookToDomain(Mockito.any(Book.class))).thenReturn(saveBook);
		when(bookDomainMapper.domainToBook(Mockito.any(BookDomain.class))).thenReturn(new Book());
		when(reviewService.getBookAverageRating(Mockito.any(Long.class))).thenReturn(Double.valueOf(0));
		BookDomain book = bookService.read(1);

		assertEquals("test", book.getName());
		assertEquals("test", book.getAuthors().stream().findFirst().get().getName());
	}

	@Test
	public void whenReadBooks_ReturnBooks() {
		List<Book> books = new ArrayList<>();
		Book saveBook1 = new Book();
		Book saveBook2 = new Book();
		Book saveBook3 = new Book();

		books.add(saveBook1);
		books.add(saveBook2);
		books.add(saveBook3);

		BookDomain bookDomain = new BookDomain();
		bookDomain.setReviews(new ArrayList<>());
		bookDomain.setAuthors(new ArrayList<>());
		when(bookRepository.findAll(Mockito.any(Specification.class))).thenReturn(books);
		when(bookDomainMapper.bookToDomain(Mockito.any(Book.class))).thenReturn(bookDomain);
		when(bookRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(new Book()));
		when(reviewService.getBookAverageRating(Mockito.any(Long.class))).thenReturn(Double.valueOf(0));

		List<BookDomain> booksResponse = bookService.readAll(new BookFilterDTO());
		assertEquals(3, booksResponse.size());
	}

	@Test
	public void whenGetRating_ReturnRating() {
		Book saveBook = new Book();
		when(bookRepository.findById(1L)).thenReturn(Optional.of(saveBook));
		when(reviewService.getBookAverageRating(Mockito.any(Long.class))).thenReturn(Double.valueOf(0));

		double rating = bookService.averageRating(1);
		assertEquals(0, rating, 0.1);
	}
}
