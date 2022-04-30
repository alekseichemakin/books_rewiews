package ru.lexa.books_reviews.controller;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lexa.books_reviews.dto.BookDTO;
import ru.lexa.books_reviews.model.Book;
import ru.lexa.books_reviews.repository.BookRepository;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {

    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @Before
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private BookRepository repository;

    @After
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    public void whenCreatePerson_thenStatus201() {
        BookDTO book = new BookDTO();
        book.setName("test");
        book.setAuthor("test");
        ResponseEntity<BookDTO> response = restTemplate.postForEntity("/api/books", book, BookDTO.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getBody().getId(), notNullValue());
        assertThat(response.getBody().getName(), is("test"));
    }

    @Test
    public void whenCreateBookWithEmptyName_thenStatus400() {
        BookDTO bookDTO = new BookDTO();

        bookDTO.setAuthor("test");
        given().log().body()
                .contentType("application/json").body(bookDTO)
                .when().post("/api/books")
                .then().log().body()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private Book createTestBook(String name, String author, String description) {
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setDescription(description);
        return repository.save(book);
    }
}
