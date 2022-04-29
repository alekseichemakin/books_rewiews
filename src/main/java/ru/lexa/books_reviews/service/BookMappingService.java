package ru.lexa.books_reviews.service;

import ru.lexa.books_reviews.controller.dto.BookDTO;
import ru.lexa.books_reviews.repository.entity.Book;

public interface BookMappingService {
    BookDTO mapToBookDto(Book entity);
    Book mapToBookEntity(BookDTO dto);
}
