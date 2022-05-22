package ru.lexa.books_reviews.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lexa.books_reviews.configuration.WebConfig;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.repository.entity.Review;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@Sql(executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts="classpath:/ReviewRepositoryTest.sql")
@Sql(executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts="classpath:/ClearReviewRepositoryTest.sql")
@DataJpaTest
public class ReviewRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ReviewRepository reviewRepository;



	@Test
	//TODO сделать предзаполнение через SQL
	public void whenFindBookReviews_thenReturnReviews() {
		// when
		List<Review> found = reviewRepository.findAllBooksReviews();

		// then
		assertEquals(found.size(), 2);

	}

	@Test
	public void whenFindFilmReviews_thenReturnReviews() {
		// when
		List<Review> found = reviewRepository.findAllFilmsReviews();

		// then
		assertEquals(found.size(), 1);
	}
}
