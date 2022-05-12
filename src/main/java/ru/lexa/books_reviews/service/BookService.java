package ru.lexa.books_reviews.service;


import ru.lexa.books_reviews.controller.dto.book.BookFilterDTO;
import ru.lexa.books_reviews.exception.InputErrorException;
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
     * @throws InputErrorException, если книга с данным именнем существует
     */
    Book create(Book book);

    /**
     * Возвращает книгу по ID
     * @param id - ID книги
     * @return - объект книги с заданным ID
     * @throws InputErrorException, если нет книги с данным ID
     */
    Book read(long id);

    /**
     * Обновляет книгу с заданным ID,
     * в соответствии с переданной книгой
     * @param book - книга в соответсвии с которой нужно обновить данные
     * @return - объект обновленной книги
     * @throws InputErrorException, если нет книги с данным ID или книга с данным именем уже существует
     */
    Book update(Book book);

    /**
     * Удаляет книгу с заданным ID
     * @param id - id книги, которого нужно удалить
     * @throws InputErrorException, если нет книги с данным ID
     */
    void delete(long id);

    /**
     * Вощвращает средний рейтинг отзвов по книги с переданным ID
     * @param id - id книги
     * @return - средний рейтинг
     * @throws InputErrorException, если нет книги с данным ID
     */
    double averageRating(long id);

    /**
     * Возвращает список всех имеющихся книг
     *
     * @return список книг
     */
    List<Book> readAll(BookFilterDTO filter);
}
