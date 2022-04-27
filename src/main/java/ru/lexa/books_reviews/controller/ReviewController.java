package ru.lexa.books_reviews.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.lexa.books_reviews.dto.ReviewDTO;
import ru.lexa.books_reviews.service.ReviewMappingService;
import ru.lexa.books_reviews.service.ReviewService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews/")
@Validated
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewMappingService reviewMappingService;

    @ApiOperation(value = "добавить новый отзыв")
    @PostMapping
    public ReviewDTO addNewReview(@Valid @RequestBody ReviewDTO dto) {
        return reviewMappingService.mapToReviewDto(reviewService.create(reviewMappingService.mapToReviewEntity(dto)));
    }

    @ApiOperation(value = "получить все отзывы")
    @GetMapping
    public Collection<ReviewDTO> getAll() {
        return reviewService.readAll().stream()
                .map(review -> reviewMappingService.mapToReviewDto(review))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "получить отзыв")
    @GetMapping("{id}")
    public ReviewDTO getReview(@PathVariable long id) {
        return reviewMappingService.mapToReviewDto(reviewService.read(id));
    }

    @ApiOperation(value = "изменить отзыв")
    @PutMapping
    public ReviewDTO updateReview(@Valid @RequestBody ReviewDTO dto) {
        return reviewMappingService.mapToReviewDto(reviewService.update(reviewMappingService.mapToReviewEntity(dto)));
    }

    @ApiOperation(value = "удалить отзыв")
    @DeleteMapping("/{id}")
    public void deleteSurvey(@PathVariable long id) {
        reviewService.delete(id);
    }
}
