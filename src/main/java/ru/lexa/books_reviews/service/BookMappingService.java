package ru.lexa.books_reviews.service;

import ru.lexa.books_reviews.dto.BookDTO;
import ru.lexa.books_reviews.model.Book;

public interface BookMappingService {
    BookDTO mapToBookDto(Book entity);
    Book mapToBookEntity(BookDTO dto);
}
