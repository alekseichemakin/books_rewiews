package ru.lexa.books_reviews.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import ru.lexa.books_reviews.repository.ReviewRepository;
import ru.lexa.books_reviews.repository.entity.Review;
import ru.lexa.books_reviews.service.implementation.ReviewServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceTest {
	@Mock
	ReviewRepository reviewRepository;

	@InjectMocks
	ReviewServiceImpl reviewService;

	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void whenSaveReview_ReturnReview() {
		when(reviewRepository.save(Mockito.any(Review.class))).thenAnswer(i -> i.getArguments()[0]);

		Review saveReview = new Review();
		saveReview.setText("test");
		saveReview.setRating(5);
		Review review = reviewService.create(saveReview);

		assertEquals("test", review.getText());
		assertEquals(5, review.getRating());
	}

	@Test
	public void whenReadReview_ReturnReview() {
		Review saveReview = new Review();
		saveReview.setText("test");
		saveReview.setRating(5);
		saveReview.setId(1L);
		when(reviewRepository.findById(1L)).thenReturn(Optional.of(saveReview));

		Review review = reviewService.read(1);

		assertEquals(5, review.getRating());
		assertEquals("test", review.getText());
	}

	@Test
	public void whenReadReviews_ReturnReviews() {
		List<Review> reviews = new ArrayList<>();
		Review review1 = new Review();
		Review review2 = new Review();
		Review review3 = new Review();

		reviews.add(review1);
		reviews.add(review2);
		reviews.add(review3);
		when(reviewRepository.findAll()).thenReturn(reviews);

		List<Review> reviewsResponse = reviewService.readAll();
		assertEquals(3, reviewsResponse.size());
	}
}
