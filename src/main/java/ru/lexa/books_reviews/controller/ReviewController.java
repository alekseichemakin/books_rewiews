package ru.lexa.books_reviews.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.lexa.books_reviews.model.Review;
import ru.lexa.books_reviews.service.ReviewService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/reviews/")
@Validated
public class ReviewController {
	@Autowired
	private ReviewService reviewService;

	@ApiOperation(value = "добавить новый отзыв")
	@PostMapping
	public Review addNewReview(@Valid @RequestBody Review review) {
		return reviewService.create(review);
	}

	@ApiOperation(value = "получить все отзывы")
	@GetMapping
	public Collection<Review> getAll() {
		return reviewService.readAll();
	}

	@ApiOperation(value = "получить отзыв")
	@GetMapping("{id}")
	public Review getBook(@PathVariable Long id) {
		return reviewService.read(id);
	}

	@ApiOperation(value = "изменить отзыв")
	@PutMapping
	public Review updateReview(@Valid @RequestBody Review review) {
		return reviewService.update(review);
	}

	@ApiOperation(value = "удалить отзыв")
	@DeleteMapping("/{id}")
	public String deleteSurvey(@PathVariable Long id) {
		reviewService.delete(id);
		return String.format("отзыв с идентификором %d был удалён", id);
	}
}
