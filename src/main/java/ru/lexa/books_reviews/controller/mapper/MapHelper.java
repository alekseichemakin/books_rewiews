package ru.lexa.books_reviews.controller.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.lexa.books_reviews.controller.dto.author.AuthorResponseDTO;
import ru.lexa.books_reviews.controller.dto.book.BookResponseDTO;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.mapper.AuthorDomainMapper;
import ru.lexa.books_reviews.repository.mapper.BookDomainMapper;
import ru.lexa.books_reviews.service.BookService;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class MapHelper {

	private BookService bookService;

	private BookMapper bookMapper;

	private AuthorMapper authorMapper;

	private AuthorDomainMapper authorDomainMapper;

	private BookDomainMapper bookDomainMapper;

	 public BookResponseDTO bookMapHelper(Book book) {
//		int reviewCount = book.getReviews() == null ? 0 : book.getReviews().size();
//		double avgRating = bookService.averageRating(book.getId());
//		List<Long> authorIds = new ArrayList<>();
//		book.getAuthors().forEach(author -> authorIds.add(author.getId()));
		return bookMapper.bookToDto(bookDomainMapper.bookToDomain(book));
	}

	public AuthorResponseDTO authorMapHelper(Author author) {
//		List<Long> bookIds = new ArrayList<>();
//		List<Long> filmIds = new ArrayList<>();
//		author.getFilms().forEach(film -> filmIds.add(film.getId()));
//		author.getBooks().forEach(book -> bookIds.add(book.getId()));
		return authorMapper.authorToDto(authorDomainMapper.authorToDomain(author));
	}
}
