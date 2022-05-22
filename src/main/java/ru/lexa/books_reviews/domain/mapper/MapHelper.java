package ru.lexa.books_reviews.domain.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import ru.lexa.books_reviews.controller.dto.author.AuthorResponseDTO;
import ru.lexa.books_reviews.controller.dto.book.BookResponseDTO;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.service.AuthorService;
import ru.lexa.books_reviews.service.BookService;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class MapHelper {

	private BookService bookService;

	private BookMapper bookMapper;

	private AuthorMapper authorMapper;

	 public BookResponseDTO bookMapHelper(Book book) {
		int reviewCount = book.getReview() == null ? 0 : book.getReview().size();
		double avgRating = bookService.averageRating(book.getId());
		List<Long> authorIds = new ArrayList<>();
		book.getAuthors().forEach(author -> authorIds.add(author.getId()));
		return bookMapper.bookToDto(book, reviewCount, avgRating, authorIds);
	}

	public AuthorResponseDTO authorMapHelper(Author author) {
		List<Long> bookIds = new ArrayList<>();
		List<Long> filmIds = new ArrayList<>();
		author.getFilms().forEach(film -> filmIds.add(film.getId()));
		author.getBooks().forEach(book -> bookIds.add(book.getId()));
		return authorMapper.authorToDto(author, bookIds, filmIds);
	}
}
