package ru.lexa.books_rewiews.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.lexa.books_rewiews.model.Book;
import ru.lexa.books_rewiews.service.BookService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @ApiOperation(value = "добавить новую книгу")
    @PostMapping
    public Book addNewBook(@Valid @RequestBody Book book) {
        return bookService.create(book);
    }

    @ApiOperation(value = "получить все книги")
    @GetMapping
    public Collection<Book> getAll() {
        return bookService.readAll();
    }

    @ApiOperation(value = "получить книгу")
    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        return bookService.read(id);
    }

    @ApiOperation(value = "изменить имя книги")
    @PutMapping("/update_name")
    public Book updateName(@Valid @RequestBody Book book) {
        return bookService.updateBookName(book);
    }

    @ApiOperation(value = "изменить описание книги")
    @PutMapping("/update_description")
    public Book updateDescription(@RequestBody Book book) {
        return bookService.updateBookDesc(book);
    }

    @ApiOperation(value = "изменить автора книги")
    @PutMapping("/update_author")
    public Book updateAuthor(@RequestBody Book book) {
        return bookService.updateBookAuthor(book);
    }

    @ApiOperation(value = "удалить книгу")
    @DeleteMapping("/delete/{id}")
    public String deleteSurvey(@PathVariable Long id) {
        if (bookService.delete(id))
            return String.format("книга с идентификором %d была удалена", id);
        else
            return String.format("книга с идентификором %d не была удалена", id);
    }
}