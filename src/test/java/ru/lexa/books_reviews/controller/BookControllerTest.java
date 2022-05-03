package ru.lexa.books_reviews.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
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
import ru.lexa.books_reviews.repository.BookRepository;
import ru.lexa.books_reviews.repository.ReviewRepository;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Review;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

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
	private BookRepository bookRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Before
	public void resetDb() {
		bookRepository.deleteAll();
		reviewRepository.deleteAll();
	}

	@Test
	public void whenCreateBook_thenStatus201() {
		BookDTO book = new BookDTO();
		book.setName("test");
		book.setAuthor("test");
		given().log().body()
				.contentType(ContentType.JSON).body(book)
				.when().post("/api/books")
				.then().log().body()
				.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void whenCreateBookWithEmptyNameAndAuthor_thenStatus400() {
		BookDTO bookDTO = new BookDTO();

		bookDTO.setAuthor("test");
		bookDTO.setName("");
		given().log().body()
				.contentType(ContentType.JSON).body(bookDTO)
				.when().post("/api/books")
				.then().log().body()
				.statusCode(HttpStatus.BAD_REQUEST.value());

		bookDTO.setAuthor("");
		bookDTO.setName("test");
		given().log().body()
				.contentType(ContentType.JSON).body(bookDTO)
				.when().post("/api/books")
				.then().log().body()
				.statusCode(HttpStatus.BAD_REQUEST.value());

		bookDTO.setAuthor(null);
		bookDTO.setName(null);
		given().log().body()
				.contentType(ContentType.JSON).body(bookDTO)
				.when().post("/api/books")
				.then().log().body()
				.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void whenCreateBookWithDuplicateName_thenStatus400() {
		createTestBook("test", "test", "test");
		BookDTO book = new BookDTO();
		book.setName("test");
		book.setAuthor("test");
		given().log().body()
				.contentType(ContentType.JSON).body(book)
				.when().post("/api/books")
				.then().log().body()
				.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void whenGetBook_thenStatus200() {
		long id = createTestBook("test", "test", "test").getId();

		given()
				.contentType(ContentType.JSON)
				.when().get("/api/books/" + id)
				.then().assertThat().log().all().statusCode(200)
				.body("id", equalTo(Long.valueOf(id).intValue()))
				.body("name", equalTo("test"))
				.body("author", equalTo("test"));
	}

	@Test
	public void whenGetNotExistsBook_thenStatus400() {
		given()
				.contentType(ContentType.JSON)
				.when().get("/api/books/999")
				.then().assertThat().log().all().statusCode(400);
	}

	@Test
	public void whenGetBooks_thenStatus200() {
		when().get("/api/books")
				.then().log().body()
				.statusCode(HttpStatus.OK.value())
				.body("$.size()", Matchers.equalTo(0));

		createTestBook("test1", "test1", "test1");
		createTestBook("test2", "test2", "test2");

		when().get("/api/books")
				.then().log().body()
				.statusCode(HttpStatus.OK.value())
				.body("$.size()", Matchers.equalTo(2))
				.and().body("get(0).name", equalTo("test1"))
				.and().body("get(1).author", equalTo("test2"));
	}

	@Test
	public void whenGetBooksWithParams_thenStatus200() {
		createTestBook("In Search of Lost Time", "Marcel Proust", "Swann's Way, the first part of A la recherche de temps perdu, Marcel Proust's seven-part cycle, was published in 1913. In it, Proust introduces the themes that run through the entire work.");
		createTestBook("Ulysses", "James Joyce", "Ulysses chronicles the passage of Leopold Bloom through Dublin during an ordinary day, June 16, 1904.");
		createTestBook("Don Quixote", "Miguel de Cervantes", "Alonso Quixano, a retired country gentleman in his fifties, lives in an unnamed section of La Mancha with his niece and a housekeeper.");
		createTestBook("One Hundred Years of Solitude", "Gabriel Garcia Marquez", "One of the 20th century's enduring works, One Hundred Years of Solitude is a widely beloved and acclaimed novel known throughout the world, and the ultimate achievement in a Nobel Prize–winning car...");
		createTestBook("The Brothers Karamazov", "Fyodor Dostoyevsky", "Dostoevsky's last and greatest novel, The Karamazov Brothers, is both a brilliantly told crime story and a passionate philosophical debate.");
		createTestBook("Crime and Punishment", "Fyodor Dostoyevsky", "It is a murder story, told from a murder;s point of view, that implicates even the most innocent reader in its enormities.");

		when().get("/api/books?author=test")
				.then().log().body()
				.statusCode(HttpStatus.OK.value())
				.body("$.size()", Matchers.equalTo(0));

		when().get("/api/books?author=Dostoyevsky")
				.then().log().body()
				.statusCode(HttpStatus.OK.value())
				.body("$.size()", Matchers.equalTo(2))
				.body("get(0).name", equalTo("The Brothers Karamazov"))
				.body("get(1).name", equalTo("Crime and Punishment"));

		when().get("/api/books?name=yss")
				.then().log().body()
				.statusCode(HttpStatus.OK.value())
				.body("$.size()", Matchers.equalTo(1))
				.body("get(0).name", equalTo("Ulysses"));

		when().get("/api/books?author=Fyodor&name=Crime")
				.then().log().body()
				.statusCode(HttpStatus.OK.value())
				.body("$.size()", Matchers.equalTo(1))
				.body("get(0).name", equalTo("Crime and Punishment"));

		when().get("/api/books?description=country gentleman in his fifties")
				.then().log().body()
				.statusCode(HttpStatus.OK.value())
				.body("$.size()", Matchers.equalTo(1))
				.body("get(0).name", equalTo("Don Quixote"));
	}

	@Test
	public void whenGetBooksWithReviewText_thenStatus200() {
		Book book1 = createTestBook("In Search of Lost Time", "Marcel Proust", "Swann's Way, the first part of A la recherche de temps perdu, Marcel Proust's seven-part cycle, was published in 1913. In it, Proust introduces the themes that run through the entire work.");
		Book book2 = createTestBook("Ulysses", "James Joyce", "Ulysses chronicles the passage of Leopold Bloom through Dublin during an ordinary day, June 16, 1904.");
		Book book3 = createTestBook("Don Quixote", "Miguel de Cervantes", "Alonso Quixano, a retired country gentleman in his fifties, lives in an unnamed section of La Mancha with his niece and a housekeeper.");
		Book book4 = createTestBook("One Hundred Years of Solitude", "Gabriel Garcia Marquez", "One of the 20th century's enduring works, One Hundred Years of Solitude is a widely beloved and acclaimed novel known throughout the world, and the ultimate achievement in a Nobel Prize–winning car...");
		Book book5 = createTestBook("The Brothers Karamazov", "Fyodor Dostoyevsky", "Dostoevsky's last and greatest novel, The Karamazov Brothers, is both a brilliantly told crime story and a passionate philosophical debate.");
		Book book6 = createTestBook("Crime and Punishment", "Fyodor Dostoyevsky", "It is a murder story, told from a murder;s point of view, that implicates even the most innocent reader in its enormities.");

		Review review1 = createTestReview(book1, 3, "best book, i like that");
		Review review2 = createTestReview(book2, 2, "i like that book");
		Review review3 = createTestReview(book3, 4, "book book book");
		Review review4 = createTestReview(book4, 5, "like this book");
		Review review5 = createTestReview(book5, 7, "comment");
		Review review6 = createTestReview(book6, 8, "wonderful");

		when().get("/api/books?reviewText=test")
				.then().log().body()
				.statusCode(HttpStatus.OK.value())
				.body("$.size()", Matchers.equalTo(0));

		when().get("/api/books?reviewText=book")
				.then().log().body()
				.statusCode(HttpStatus.OK.value())
				.body("$.size()", Matchers.equalTo(4));

		when().get("/api/books?reviewText=like&author=Jame")
				.then().log().body()
				.statusCode(HttpStatus.OK.value())
				.body("$.size()", Matchers.equalTo(1))
				.body("get(0).name", equalTo("Ulysses"));
	}

	@Test
	public void whenUpdateBook_thenStatus200() throws JSONException {

		long id = createTestBook("In Search of Lost Time", "Marcel Proust", "description").getId();

		JSONObject newBook = new JSONObject();

		newBook.put("author", "John");
		newBook.put("name", "Book");

		given()
				.contentType(ContentType.JSON).body(newBook.toString())
				.when().put("/api/books/" + id)
				.then().log().body()
				.statusCode(HttpStatus.OK.value())
				.body("name", equalTo("Book"))
				.body("author", equalTo("John"));

		JSONObject updDesc = new JSONObject();
		updDesc.put("description", "test");

		given()
				.contentType(ContentType.JSON).body(updDesc.toString())
				.when().put("/api/books/" + id)
				.then().log().body()
				.statusCode(HttpStatus.OK.value())
				.body("name", equalTo("Book"))
				.body("author", equalTo("John"))
				.body("description", equalTo("test"));
	}

	@Test
	public void whenUpdateBook_thenStatus400() throws JSONException {
		long id = createTestBook("In Search of Lost Time", "Marcel Proust", "description").getId();
		createTestBook("test", "test", "");
		JSONObject newBook = new JSONObject();

		newBook.put("author", "John");
		newBook.put("name", "Book");

		given()
				.contentType(ContentType.JSON).body(newBook.toString())
				.when().put("/api/books/" + 4)
				.then().log().body()
				.statusCode(HttpStatus.BAD_REQUEST.value());

		newBook.put("name", "");

		given()
				.contentType(ContentType.JSON).body(newBook.toString())
				.when().put("/api/books/" + id)
				.then().log().body()
				.statusCode(HttpStatus.BAD_REQUEST.value());

		newBook.put("name", "test");

		given()
				.contentType(ContentType.JSON).body(newBook.toString())
				.when().put("/api/books/" + id)
				.then().log().body()
				.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void whenDelete_thenStatus200() {
		long id = createTestBook("In Search of Lost Time", "Marcel Proust", "description").getId();

		given().pathParam("id", id).log().body().contentType("application/json")
				.when().delete("/api/books/{id}")
				.then().log().body()
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void whenDelete_thenStatus400() {
		given().pathParam("id", 4).log().body().contentType("application/json")
				.when().delete("/api/books/{id}")
				.then().log().body()
				.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void whenGetAverageRating_thenStatus200() {
		Book book1 = createTestBook("In Search of Lost Time", "Marcel Proust", "Lorem ipsum");
		Review review1 = createTestReview(book1, 7, "best book, i like that");
		Review review2 = createTestReview(book1, 5, "i like that book");

		when().get("/api/books/" + book1.getId() + "/averageRating")
				.then().log().body()
				.statusCode(HttpStatus.OK.value())
				.equals(6.0);
	}

	@Test
	public void whenGetReviews_thenStatus200() {
		Book book1 = createTestBook("In Search of Lost Time", "Marcel Proust", "Lorem ipsum");
		Review review1 = createTestReview(book1, 7, "best book, i like that");
		Review review2 = createTestReview(book1, 5, "i like that book");

		when().get("/api/books/" + book1.getId() + "/reviews")
				.then().log().body()
				.statusCode(HttpStatus.OK.value())
				.body("$.size()", equalTo(2))
				.body("get(0).rating", equalTo(7));
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
