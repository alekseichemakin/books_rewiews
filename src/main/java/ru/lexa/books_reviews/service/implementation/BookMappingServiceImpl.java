package ru.lexa.books_reviews.service.implementation;

import org.springframework.stereotype.Service;
import ru.lexa.books_reviews.dto.BookDTO;
import ru.lexa.books_reviews.model.Book;
import ru.lexa.books_reviews.service.BookMappingService;

@Service
public class BookMappingServiceImpl implements BookMappingService {

    @Override
    public BookDTO mapToBookDto(Book entity) {
        BookDTO dto = new BookDTO();
        dto.setAuthor(entity.getAuthor());
        dto.setDescription(entity.getDescription());
        dto.setName(entity.getName());
        dto.setId(entity.getId());
        return dto;
    }

    @Override
    public Book mapToBookEntity(BookDTO dto) {
        Book book = new Book();
        book.setAuthor(dto.getAuthor());
        book.setDescription(dto.getDescription());
        book.setName(dto.getName());
        book.setId(dto.getId());
        return book;
    }
}
