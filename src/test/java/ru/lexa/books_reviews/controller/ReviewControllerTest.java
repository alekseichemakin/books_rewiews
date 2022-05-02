package ru.lexa.books_reviews.controller;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lexa.books_reviews.controller.dto.BookDTO;
import ru.lexa.books_reviews.controller.dto.ReviewDTO;
import ru.lexa.books_reviews.repository.BookRepository;
import ru.lexa.books_reviews.repository.ReviewRepository;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Review;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReviewControllerTest {
    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @Before
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @AfterEach
    public void resetDb() {
        bookRepository.deleteAll();
        reviewRepository.deleteAll();
    }

    @Test
    public void whenCreateReview_thenStatus201() {
        long id = createTestBook("test", "test", "test").getId();
        ReviewDTO reviewDTO = new ReviewDTO();

        reviewDTO.setRating(5);
        reviewDTO.setText("text");
        reviewDTO.setBook_id(id);

        given().log().body()
                .contentType(ContentType.JSON).body(reviewDTO)
                .when().post("/api/reviews")
                .then().log().body()
                .statusCode(HttpStatus.CREATED.value());
    }

    private Book createTestBook(String name, String author, String description) {
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setDescription(description);
        return bookRepository.save(book);
    }

    private Review createTestReview(Book book, int rating, String text) {
        Review review = new Review();
        review.setBook(book);
        review.setRating(rating);
        review.setText(text);
        return reviewRepository.save(review);
    }
}
