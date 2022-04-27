package ru.lexa.books_reviews.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.lexa.books_reviews.dto.BookDTO;
import ru.lexa.books_reviews.dto.ReviewDTO;
import ru.lexa.books_reviews.service.BookMappingService;
import ru.lexa.books_reviews.service.BookService;
import ru.lexa.books_reviews.service.ReviewMappingService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookMappingService bookMappingService;

    @Autowired
    private ReviewMappingService reviewMappingService;

    @ApiOperation(value = "добавить новую книгу")
    @PostMapping
    public BookDTO addNewBook(@Valid @RequestBody BookDTO dto) {
        return bookMappingService.mapToBookDto(bookService.create(bookMappingService.mapToBookEntity(dto)));
    }

    @ApiOperation(value = "получить книги")
    @GetMapping
    public Collection<BookDTO> getAll(@RequestParam(required = false) String author,
                                   @RequestParam(required = false) String description,
                                   @RequestParam(required = false) String name,
                                   @RequestParam(required = false) String reviewText) {
        HashMap<String, String> params = new HashMap<>();
        params.put("author", author);
        params.put("description", description);
        params.put("name", name);
        params.put("reviewText", reviewText);
        return bookService.readFilter(params).stream()
                .map(book -> bookMappingService.mapToBookDto(book))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "получить книгу")
    @GetMapping("/{id}")
    public BookDTO getBook(@PathVariable long id) {
        return bookMappingService.mapToBookDto(bookService.read(id));
    }

    @ApiOperation(value = "изменить книгу")
    @PutMapping
    public BookDTO updateBook(@Valid @RequestBody BookDTO dto) {
        return bookMappingService.mapToBookDto(bookService.update(bookMappingService.mapToBookEntity(dto)));
    }

    @ApiOperation(value = "удалить книгу")
    @DeleteMapping("/{id}")
    public void deleteSurvey(@PathVariable long id) {
        bookService.delete(id);
    }

    @ApiOperation(value = "получить отзывы к книге")
    @GetMapping("/{id}/reviews")
    public Collection<ReviewDTO> getReviews(@PathVariable long id) {
        return bookService.read(id).getReview().stream()
                .map(review -> reviewMappingService.mapToReviewDto(review))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "получить среднюю оценку")
    @GetMapping("/{id}/averageRating")
    public double getAverage(@PathVariable long id) {
        return bookService.averageRating(id);
    }
}