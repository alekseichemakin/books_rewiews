package ru.lexa.books_reviews.controller;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lexa.books_reviews.controller.dto.review.ReviewDTO;
import ru.lexa.books_reviews.controller.dto.review.ReviewResponseDTO;
import ru.lexa.books_reviews.repository.BookRepository;
import ru.lexa.books_reviews.repository.ReviewRepository;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Review;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

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

    @Before
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

        ReviewDTO reviewDTO2 = new ReviewDTO();

        reviewDTO2.setRating(8);
        reviewDTO2.setText("text");
        reviewDTO2.setBook_id(id);

        given().log().body()
                .contentType(ContentType.JSON).body(reviewDTO2)
                .when().post("/api/reviews")
                .then().log().body()
                .statusCode(HttpStatus.CREATED.value());

        when().get("/api/books/" + id + "/reviews")
                .then().log().body()
                .statusCode(HttpStatus.OK.value())
                .body("$.size()", equalTo(2))
                .body("get(0).rating", equalTo(5));
    }

    @Test
    public void whenCreateReviewWithWrongRatingAndBookId_thenStatus400() {
        long id = createTestBook("test", "test", "test").getId();
        ReviewDTO reviewDTO = new ReviewDTO();

        reviewDTO.setRating(0);//should be 1-10
        reviewDTO.setText("text");
        reviewDTO.setBook_id(id);

        given().log().body()
                .contentType(ContentType.JSON).body(reviewDTO)
                .when().post("/api/reviews")
                .then().log().body()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        reviewDTO.setRating(11);//should be 1-10

        given().log().body()
                .contentType(ContentType.JSON).body(reviewDTO)
                .when().post("/api/reviews")
                .then().log().body()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        reviewDTO.setBook_id(777);
        reviewDTO.setRating(5);

        given().log().body()
                .contentType(ContentType.JSON).body(reviewDTO)
                .when().post("/api/reviews")
                .then().log().body()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void whenGetReviews_thenStatus200() {
        Book book1 = createTestBook("test", "test", "test");
        Book book2 = createTestBook("test2", "test2", "test2");

        createTestReview(book1, 3, "text");
        createTestReview(book1, 4, "extt");
        createTestReview(book2, 5, "xett");
        createTestReview(book2, 6, "txet");
        createTestReview(book2, 7, "ttex");

        given()
                .contentType(ContentType.JSON)
                .when().get("/api/reviews")
                .then().assertThat().log().all().statusCode(HttpStatus.OK.value())
                .body("$.size()", equalTo(5));
    }

    @Test
    public void whenGetReview_thenStatus200() {
        Book book1 = createTestBook("test", "test", "test");

        long id = createTestReview(book1, 3, "text").getId();

        given()
                .contentType(ContentType.JSON)
                .when().get("/api/reviews/" + id)
                .then().assertThat().log().all().statusCode(HttpStatus.OK.value())
                .body("rating", equalTo(3));
    }

    @Test
    public void whenGetReview_thenStatus400() {
        given()
                .contentType(ContentType.JSON)
                .when().get("/api/reviews/" + 400)
                .then().assertThat().log().all().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void whenUpdateReview_thenStatus200() throws JSONException {

        Book book1 = createTestBook("test", "test", "test");
        Review review = createTestReview(book1, 3, "text");

        ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO();
        reviewResponseDTO.setId(review.getId());
        reviewResponseDTO.setRating(7);
        reviewResponseDTO.setText("test");
        reviewResponseDTO.setBook_id(book1.getId());

        given().log().body()
                .contentType(ContentType.JSON).body(reviewResponseDTO)
                .when().put("/api/reviews")
                .then().log().body()
                .statusCode(HttpStatus.OK.value())
                .body("text", equalTo("test"))
                .body("rating", equalTo(7));
    }

    @Test
    public void whenUpdateBook_thenStatus400() throws JSONException {
        Book book1 = createTestBook("test", "test", "test");
        Review review = createTestReview(book1, 3, "text");

        ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO();
        reviewResponseDTO.setId(400);
        reviewResponseDTO.setRating(7);
        reviewResponseDTO.setText("test");
        reviewResponseDTO.setBook_id(book1.getId());

        given().log().body()
                .contentType(ContentType.JSON).body(reviewResponseDTO)
                .when().put("/api/reviews")
                .then().log().body()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        reviewResponseDTO.setId(review.getId());
        reviewResponseDTO.setRating(0);
        given().log().body()
                .contentType(ContentType.JSON).body(reviewResponseDTO)
                .when().put("/api/reviews")
                .then().log().body()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        reviewResponseDTO.setRating(11);
        given().log().body()
                .contentType(ContentType.JSON).body(reviewResponseDTO)
                .when().put("/api/reviews")
                .then().log().body()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void whenDelete_thenStatus200() {
        Book book1 = createTestBook("test", "test", "test");
        long id = createTestReview(book1, 3, "text").getId();

        given().pathParam("id", id).log().body().contentType("application/json")
                .when().delete("/api/reviews/{id}")
                .then().log().body()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void whenDelete_thenStatus400() {
        given().pathParam("id", 400).log().body().contentType("application/json")
                .when().delete("/api/books/{id}")
                .then().log().body()
                .statusCode(HttpStatus.BAD_REQUEST.value());
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
