package ru.lexa.books_reviews.reviews.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lexa.books_reviews.reviews.repository.entity.Review;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts="classpath:/ReviewRepositoryTest.sql")
public class ReviewRepositoryTest {

	@Autowired
	private ReviewRepository reviewRepository;


	@Test
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

	@Test
	public void whenGetAverageRating_thenReturnRating() {
		// when
		double rating = reviewRepository.getAverageRating(1);

		// then
		assertEquals(rating, 5.5, 0.1);
	}
}
