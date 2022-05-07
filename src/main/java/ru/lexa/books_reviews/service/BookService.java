package ru.lexa.books_reviews.service;


import ru.lexa.books_reviews.controller.dto.BookFilterDTO;
import ru.lexa.books_reviews.repository.entity.Book;

import java.util.List;

/**
 * Сервис обрабатывающий запорсы с контроллера {@link ru.lexa.books_reviews.controller.BookController}
 */
public interface BookService {
    /**
     * Создает книгу с заданными параметрми
     * @param book - книга для создания
     * @return созданную книгу
     */
    Book create(Book book);

    /**
     * Возвращает книгу по ID
     * @param id - ID книги
     * @return - объект книги с заданным ID
     */
    Book read(long id);

    /**
     * Обновляет книгу с заданным ID,
     * в соответствии с переданной книгой
     * @param book - книга в соответсвии с которой нужно обновить данные
     * @return - объект обновленной книги
     */
    Book update(Book book);

    /**
     * Удаляет книгу с заданным ID
     * @param id - id книги, которого нужно удалить
     */
    void delete(long id);

    /**
     * Вощвращает средний рейтинг отзвов по книги с переданным ID
     * @param id - id книги
     * @return - средний рейтинг
     */
    double averageRating(long id);

    /**
     * Возвращает список всех имеющихся книг
     * @return список книг
     */
    List<Book> readAll(BookFilterDTO filter);
}
