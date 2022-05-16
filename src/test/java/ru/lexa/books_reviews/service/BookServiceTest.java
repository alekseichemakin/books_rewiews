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
import ru.lexa.books_reviews.repository.BookRepository;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Review;
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

	@InjectMocks
	BookServiceImpl bookService;


	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void whenSaveBook_ReturnBook() {
		Book saveBook = new Book();
		Author author = new Author();
		when(bookRepository.save(Mockito.any(Book.class))).thenAnswer(i -> i.getArguments()[0]);

		author.setName("test");
		saveBook.setName("test");
		saveBook.setAuthor(author);
		Book book = bookService.create(saveBook);

		assertEquals("test", book.getName());
		assertEquals("test", book.getAuthor().getName());
	}

	@Test
	public void whenReadBook_ReturnBook() {
		Book saveBook = new Book();
		Author author = new Author();
		saveBook.setId(1L);
		saveBook.setName("test");
		author.setName("test");
		saveBook.setAuthor(author);
		when(bookRepository.findById(1L)).thenReturn(Optional.of(saveBook));

		Book book = bookService.read(1);

		assertEquals("test", book.getName());
		assertEquals("test", book.getAuthor().getName());
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
		when(bookRepository.findAll(Mockito.any(Specification.class))).thenReturn(books);

		List<Book> booksResponse = bookService.readAll(new BookFilterDTO());
		assertEquals(3, booksResponse.size());
	}

	@Test
	public void whenGetRating_ReturnRating() {
		List<Review> reviews = new ArrayList<>();
		Review review1 = new Review();
		Review review2 = new Review();
		Book saveBook = new Book();
		review1.setRating(7);
		review2.setRating(5);
		reviews.add(review1);
		reviews.add(review2);
		saveBook.setReview(reviews);
		when(bookRepository.findById(1L)).thenReturn(Optional.of(saveBook));

		double rating = bookService.averageRating(1);
		assertEquals(6, rating, 0.1);
	}
}
