package ru.lexa.books_reviews.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lexa.books_reviews.configuration.WebConfig;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.repository.entity.Review;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReviewRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ReviewRepository reviewRepository;

//	@Autowired
//	private BookRepository bookRepository;
//
//	@Autowired
//	private FilmRepository filmRepository;

	@Test
	//TODO сделать предзаполнение через SQL
	public void whenFindBookReviews_thenReturnReviews() {
		// given
		Book book = new Book();
		book.setName("testBook");
		Film film = new Film();
		film.setName("testFilm");
		Review review1 = new Review();
		Review review2 = new Review();
		Review review3 = new Review();

		review1.setBook(book);
		review1.setRating(5);
		review2.setFilm(film);
		review2.setRating(5);
		review3.setBook(book);
		review3.setRating(5);
		entityManager.persist(book);
		entityManager.persist(film);
		entityManager.flush();
		entityManager.persist(review1);
		entityManager.persist(review2);
		entityManager.persist(review3);
		entityManager.flush();

		// when
		List<Review> found = reviewRepository.findAllBooksReviews();

		// then
		assertEquals(found.size(), 2);
	}

	@Test
	public void whenFindFilmReviews_thenReturnReviews() {
		// given
		Book book = new Book();
		book.setName("testBook");
		Film film = new Film();
		film.setName("testFilm");
		Review review1 = new Review();
		Review review2 = new Review();
		Review review3 = new Review();

		review1.setBook(book);
		review1.setRating(5);
		review2.setFilm(film);
		review2.setRating(5);
		review3.setBook(book);
		review3.setRating(5);
		entityManager.persist(book);
		entityManager.persist(film);
		entityManager.flush();
		entityManager.persist(review1);
		entityManager.persist(review2);
		entityManager.persist(review3);
		entityManager.flush();

		// when
		List<Review> found = reviewRepository.findAllFilmsReviews();

		// then
		assertEquals(found.size(), 1);
	}
}
