package ru.lexa.books_reviews.reviews.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import ru.lexa.books_reviews.reviews.domain.ReviewDomain;
import ru.lexa.books_reviews.reviews.repository.ReviewRepository;
import ru.lexa.books_reviews.reviews.repository.entity.Review;
import ru.lexa.books_reviews.reviews.repository.mapper.ReviewDomainMapper;
import ru.lexa.books_reviews.reviews.service.implementation.ReviewServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceTest {
	@Mock
	ReviewRepository reviewRepository;

	@Mock
	ReviewDomainMapper reviewDomainMapper;

	@InjectMocks
	ReviewServiceImpl reviewService;

	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void whenSaveReview_ReturnReview() {
		ReviewDomain saveReview = new ReviewDomain();
		saveReview.setText("test");
		saveReview.setRating(5);

		when(reviewRepository.save(Mockito.any(Review.class))).thenAnswer(i -> i.getArguments()[0]);
		when(reviewDomainMapper.reviewToDomain(Mockito.any(Review.class))).thenReturn(saveReview);
		when(reviewDomainMapper.domainToReview(Mockito.any(ReviewDomain.class))).thenReturn(new Review());

		ReviewDomain review = reviewService.create(saveReview);

		Assert.assertEquals("test", review.getText());
		Assert.assertEquals(5, review.getRating());
	}

	@Test
	public void whenReadReview_ReturnReview() {
		ReviewDomain saveReview = new ReviewDomain();
		saveReview.setText("test");
		saveReview.setRating(5);
		saveReview.setId(1L);

		when(reviewRepository.findById(1L)).thenReturn(Optional.of(new Review()));
		when(reviewDomainMapper.reviewToDomain(Mockito.any(Review.class))).thenReturn(saveReview);

		ReviewDomain review = reviewService.read(1);

		Assert.assertEquals(5, review.getRating());
		Assert.assertEquals("test", review.getText());
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
		when(reviewDomainMapper.reviewToDomain(Mockito.any(Review.class))).thenReturn(new ReviewDomain());


		List<ReviewDomain> reviewsResponse = reviewService.readAll();
		Assert.assertEquals(3, reviewsResponse.size());
	}
}
