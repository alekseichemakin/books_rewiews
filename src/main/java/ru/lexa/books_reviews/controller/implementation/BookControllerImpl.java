package ru.lexa.books_reviews.controller.implementation;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.lexa.books_reviews.controller.BookController;
import ru.lexa.books_reviews.dto.BookDTO;
import ru.lexa.books_reviews.dto.BookFilterDTO;
import ru.lexa.books_reviews.dto.ReviewDTO;
import ru.lexa.books_reviews.service.BookMappingService;
import ru.lexa.books_reviews.service.BookService;
import ru.lexa.books_reviews.service.ReviewMappingService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookControllerImpl implements BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookMappingService bookMappingService;

    @Autowired
    private ReviewMappingService reviewMappingService;

    @ApiOperation(value = "добавить новую книгу")
    @PostMapping
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO createBook(@Valid @RequestBody BookDTO dto) {
        return bookMappingService.mapToBookDto(bookService.create(bookMappingService.mapToBookEntity(dto)));
    }

    @ApiOperation(value = "получить книги")
    @GetMapping
    @Override
    public Collection<BookDTO> readAll(@RequestParam(required = false) String author,
                                       @RequestParam(required = false) String description,
                                       @RequestParam(required = false) String name,
                                       @RequestParam(required = false) String reviewText) {
        BookFilterDTO filter = new BookFilterDTO(name, description, author, reviewText);
        return bookService.readAll(filter).stream().map(book -> bookMappingService.mapToBookDto(book)).collect(Collectors.toList());
    }

    @ApiOperation(value = "получить книгу")
    @GetMapping("/{id}")
    @Override
    public BookDTO readBook(@PathVariable long id) {
        return bookMappingService.mapToBookDto(bookService.read(id));
    }

    @ApiOperation(value = "изменить книгу")
    @PutMapping("/{id}")
    @Override
    public BookDTO updateBook(@RequestBody BookDTO dto, @PathVariable long id) {
        dto.setId(id);
        return bookMappingService.mapToBookDto(bookService.update(bookMappingService.mapToBookEntity(dto)));
    }

    @ApiOperation(value = "удалить книгу")
    @DeleteMapping("/{id}")
    @Override
    public void deleteBook(@PathVariable long id) {
        bookService.delete(id);
    }

    @ApiOperation(value = "получить отзывы к книге")
    @GetMapping("/{id}/reviews")
    @Override
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