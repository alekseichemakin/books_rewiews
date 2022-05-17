package ru.lexa.books_reviews.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.jpa.domain.Specification;
import ru.lexa.books_reviews.controller.dto.book.BookFilterDTO;
import ru.lexa.books_reviews.repository.FilmRepository;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.repository.entity.Review;
import ru.lexa.books_reviews.service.implementation.FilmServiceImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FilmServiceTest {
	@Mock
	FilmRepository filmRepository;

	@InjectMocks
	FilmServiceImpl filmService;

	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void whenSaveFilm_ReturnFilm() {
		when(filmRepository.save(Mockito.any(Film.class))).thenAnswer(i -> i.getArguments()[0]);

		Book book = new Book();
		Author author = new Author();
		Film saveFilm = new Film();
		saveFilm.setName("test");
		author.setName("test");
		book.setName("test");
		saveFilm.setAuthor(author);
		saveFilm.setBook(book);
		Film film = filmService.create(saveFilm);

		assertEquals("test", film.getName());
		assertEquals("test", film.getBook().getName());
		assertEquals("test", film.getAuthor().getName());
	}

	@Test
	public void whenReadFilm_ReturnFilm() {
		Book book = new Book();
		Author author = new Author();
		Film saveFilm = new Film();
		saveFilm.setName("test");
		author.setName("test");
		book.setName("test");
		saveFilm.setAuthor(author);
		saveFilm.setBook(book);
		when(filmRepository.findById(1L)).thenReturn(Optional.of(saveFilm));

		Film film = filmService.read(1);

		assertEquals("test", film.getName());
		assertEquals("test", film.getBook().getName());
		assertEquals("test", film.getAuthor().getName());
	}

	@Test
	public void whenReadFilms_ReturnFilms() {
		List<Film> films = new ArrayList<>();
		Film saveFilm1 = new Film();
		Film saveFilm2 = new Film();
		Film saveFilm3 = new Film();

		films.add(saveFilm1);
		films.add(saveFilm2);
		films.add(saveFilm3);
		when(filmRepository.findAll()).thenReturn(films);

		List<Film> filmResponse = filmService.readAll();
		assertEquals(3, filmResponse.size());
	}
}
