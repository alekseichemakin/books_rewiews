package ru.lexa.books_reviews.service;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import ru.lexa.books_reviews.domain.BookDomain;
import ru.lexa.books_reviews.domain.FilmDomain;
import ru.lexa.books_reviews.repository.FilmRepository;
import ru.lexa.books_reviews.repository.entity.Book;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.repository.mapper.FilmDomainMapper;
import ru.lexa.books_reviews.service.implementation.FilmServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FilmServiceTest {
	@Mock
	FilmRepository filmRepository;

	@Mock
	FilmDomainMapper filmDomainMapper;

	@Mock BookService bookService;

	@InjectMocks
	FilmServiceImpl filmService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void whenSaveFilm_ReturnFilm() {
		FilmDomain saveFilm = new FilmDomain();
		saveFilm.setName("test");
		Book book = new Book();
		book.setId(0L);
		saveFilm.setBook(book);

		when(filmRepository.save(Mockito.any(Film.class))).thenAnswer(i -> i.getArguments()[0]);
		when(filmDomainMapper.filmToDomain(Mockito.any(Film.class))).thenReturn(saveFilm);
		when(filmDomainMapper.domainToFilm(Mockito.any(FilmDomain.class))).thenReturn(new Film());
		when(bookService.read(Mockito.anyLong())).thenReturn(new BookDomain());

		FilmDomain film = filmService.create(saveFilm);

		assertEquals("test", film.getName());
	}

	@Test
	public void whenReadFilm_ReturnFilm() {
		Book book = new Book();
		FilmDomain saveFilm = new FilmDomain();
		saveFilm.setName("test");
		book.setId(0L);
		saveFilm.setBook(book);

		when(filmRepository.findById(1L)).thenReturn(Optional.of(new Film()));
		when(filmDomainMapper.filmToDomain(Mockito.any(Film.class))).thenReturn(saveFilm);

		FilmDomain film = filmService.read(1);

		assertEquals("test", film.getName());
	}

	@Test
	public void whenReadFilms_ReturnFilms() {
		List<Film> films = new ArrayList<>();
		Film saveFilm1 = new Film();
		Film saveFilm2 = new Film();
		Film saveFilm3 = new Film();
		FilmDomain filmDomain = new FilmDomain();
		Book book = new Book();
		book.setId(0L);
		filmDomain.setBook(book);

		films.add(saveFilm1);
		films.add(saveFilm2);
		films.add(saveFilm3);
		when(filmRepository.findAll()).thenReturn(films);
		when(filmDomainMapper.filmToDomain(Mockito.any(Film.class))).thenReturn(filmDomain);

		List<FilmDomain> filmResponse = filmService.readAll();
		assertEquals(3, filmResponse.size());
	}
}
