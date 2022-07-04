package ru.lexa.books_reviews.controller.implementation;

import controller.BookController;
import controller.dto.book.*;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;
import ru.lexa.books_reviews.controller.mapper.BookMapper;
import ru.lexa.books_reviews.domain.BookDomain;
import ru.lexa.books_reviews.service.BookService;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Реализация контроллера {@link ru.lexa.books_reviews.controller.BookController}
 */
@AllArgsConstructor
@RestController
public class BookControllerImpl implements BookController {
	private final BookService bookService;

	private BookMapper bookMapper;

	@Override
	public BookResponseDTO createBook(BookReviewUnitedDTO bookReviewUnitedDTO) {
		BookRequestDTO dto = bookReviewUnitedDTO.getBookRequestDTO();
		BookDomain domain = bookMapper.dtoToBook(dto);
		return bookMapper.bookToDto(bookService.create(domain, bookReviewUnitedDTO.getBookReviewRequestDTO()));
	}

	@Override
	public Collection<BookResponseDTO> readAll(BookFilterDTO filterDTO) {
		return bookService.readAll(filterDTO).stream()
				.map(bookMapper::bookToDto)
				.collect(Collectors.toList());
	}

	@Override
	public BookResponseDTO readBook(long id) {
		return bookMapper.bookToDto(bookService.read(id));
	}

	@Override
	public BookResponseDTO updateBook(BookDTO dto) {
		BookDomain domain = bookMapper.dtoToBook(dto);
		return bookMapper.bookToDto(bookService.update(domain));
	}

	@Override
	public void deleteBook(long id) {
		bookService.delete(id);
	}

	@Override
	public double getAverage(long id) {
		return bookService.averageRating(id);
	}

	@RabbitListener(queues = {"${queue.name.clearBookCache}"})
	@Override
	public void clearCache(@Payload long id) {
		bookService.clearCache(id);
	}
}