package ru.lexa.books_rewiews.service.implementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lexa.books_rewiews.model.Book;
import ru.lexa.books_rewiews.repository.BookRepository;
import ru.lexa.books_rewiews.service.BookService;

import java.util.List;
import java.util.function.Consumer;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book create(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> readAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book read(long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null)
            return false;
        bookRepository.delete(book);
        return true;
    }

    @Override
    public Book update(Book book) {
        return uniUpdate(book, (currentBook) -> {
            currentBook.setName(book.getName());
            currentBook.setAuthor(book.getAuthor());
            currentBook.setDescription(book.getDescription());
        });
    }

    @Override
    public Book updateBookName(Book book) {
        return uniUpdate(book, (currentBook) -> currentBook.setName(book.getName()));
    }

    @Override
    public Book updateBookDesc(Book book) {
        return uniUpdate(book, (currentBook) -> currentBook.setDescription(book.getDescription()));
    }

    @Override
    public Book updateBookAuthor(Book book) {
        return uniUpdate(book, (currentBook) -> currentBook.setAuthor(book.getAuthor()));
    }

    private Book uniUpdate(Book book, Consumer<Book> consumer) {
        Book currentBook = bookRepository.findById(book.getId()).orElse(null);
        if (currentBook != null) {
            consumer.accept(currentBook);
        }
        return currentBook;
    }
}
