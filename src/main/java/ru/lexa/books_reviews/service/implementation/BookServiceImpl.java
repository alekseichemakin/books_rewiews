package ru.lexa.books_reviews.service.implementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lexa.books_reviews.exception.BookNotFoundException;
import ru.lexa.books_reviews.model.Book;
import ru.lexa.books_reviews.model.Review;
import ru.lexa.books_reviews.repository.BookRepository;
import ru.lexa.books_reviews.service.BookService;

import java.util.List;

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
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null)
            throw new BookNotFoundException();
        return book;
    }

    @Override
    public void delete(long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null)
            throw new BookNotFoundException();
        bookRepository.delete(book);
    }

    @Override
    public Book update(Book book) {
        Book updBook = bookRepository.findById(book.getId()).orElse(null);

        if (updBook == null)
            throw new BookNotFoundException();
        updBook.setName(book.getName());
        updBook.setAuthor(book.getAuthor());
        updBook.setDescription(book.getDescription());
        bookRepository.save(updBook);
        return updBook;
    }

    @Override
    public double averageRating(long id) {
        Book book = bookRepository.findById(id).orElse(null);

        if (book == null)
            throw new BookNotFoundException();
        if (book.getReview().size() == 0)
            return 0;
        return book.getReview().stream().mapToDouble(Review::getRating).sum() / book.getReview().size();
    }

    @Override
    public List<Book> findByName(String name) {
        return bookRepository.findBooksByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return bookRepository.findBooksByAuthorContainingIgnoreCase(author);
    }

    @Override
    public List<Book> findByDescription(String description) {
        return bookRepository.findBooksByDescriptionContainingIgnoreCase(description);
    }

    @Override
    public List<Book> findByReviewText(String text) {
        return bookRepository.findBooksByReviewText(text);
    }


}
