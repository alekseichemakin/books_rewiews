package ru.lexa.books_reviews.service.implementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lexa.books_reviews.exception.BookNotFoundException;
import ru.lexa.books_reviews.exception.ReviewNotFoundException;
import ru.lexa.books_reviews.model.Book;
import ru.lexa.books_reviews.model.Review;
import ru.lexa.books_reviews.repository.BookRepository;
import ru.lexa.books_reviews.service.BookService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book create(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book read(long id) {
        return bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    @Override
    public void delete(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        bookRepository.delete(book);
    }

    @Override
    public Book update(Book book) {
        Book updBook = bookRepository.findById(book.getId())
                .orElseThrow(BookNotFoundException::new);
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
    public List<Book> readFilter(Map<String, String> params) {
        List<Book> books = bookRepository.findAll();

        if (params.get("reviewText") != null) {
            books = findByReviewText(params.get("reviewText"));
        }
        if (params.get("author") != null) {
            books = books.stream()
                    .filter(book -> book.getAuthor().contains(params.get("author")))
                    .collect(Collectors.toList());
        }
        if (params.get("description") != null) {
            books = books.stream()
                    .filter(book -> book.getDescription().contains(params.get("description")))
                    .collect(Collectors.toList());
        }
        if (params.get("name") != null) {
            books = books.stream()
                    .filter(book -> book.getName().contains(params.get("name")))
                    .collect(Collectors.toList());
        }
        return books;
    }

    public List<Book> findByReviewText(String text) {
        return bookRepository.findBooksByReviewText(text);
    }
}
