package ru.lexa.books_reviews.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lexa.books_reviews.domain.FilmDomain;
import ru.lexa.books_reviews.exception.FilmNotFoundException;
import ru.lexa.books_reviews.exception.NameErrorException;
import ru.lexa.books_reviews.repository.AuthorFilmRepository;
import ru.lexa.books_reviews.repository.FilmRepository;
import ru.lexa.books_reviews.repository.entity.Author;
import ru.lexa.books_reviews.repository.entity.AuthorFilm;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.repository.mapper.FilmDomainMapper;
import ru.lexa.books_reviews.service.FilmService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса {@link ru.lexa.books_reviews.service.FilmService}
 */
@Transactional(readOnly = true)
@Service
@AllArgsConstructor
public class FilmServiceImpl implements FilmService {

    private FilmRepository filmRepository;

    private FilmDomainMapper filmDomainMapper;

    private AuthorFilmRepository authorFilmRepository;

    @Transactional
    @Override
    public FilmDomain create(FilmDomain film) {
        List<Author> authors = film.getAuthors();
        film.setAuthors(new ArrayList<>());
        try {
            film = filmDomainMapper.filmToDomain(filmRepository.save(filmDomainMapper.domainToFilm(film)));
        } catch (DataIntegrityViolationException e) {
            throw new NameErrorException();
        }
        FilmDomain finalFilm = film;
        authors.forEach(author -> authorFilmRepository
                .save(new AuthorFilm(author, filmDomainMapper.domainToFilm(finalFilm))));
        film.setAuthors(authors);
        return film;
    }

    @Override
    public FilmDomain read(long id) {
        return filmDomainMapper.filmToDomain(filmRepository.findById(id)
                .orElseThrow(() -> {
                    throw new FilmNotFoundException(id);
                }));
    }

    @Transactional
    @Override
    public FilmDomain update(FilmDomain film) {
        List<Author> authors = film.getAuthors();
        film.setAuthors(new ArrayList<>());
        FilmDomain finalFilm = film;
        authors.forEach(author -> authorFilmRepository
                .save(new AuthorFilm(author, filmDomainMapper.domainToFilm(finalFilm))));
        try {
            film = filmDomainMapper.filmToDomain(filmRepository.saveAndFlush(filmDomainMapper.domainToFilm(film)));
        } catch (DataIntegrityViolationException e) {
            throw new NameErrorException();
        }
        film.setAuthors(authors);
        return film;
    }

    @Transactional
    @Override
    public void delete(long id) {
        Film film = filmRepository.findById(id).orElseThrow(() -> {throw new FilmNotFoundException(id);});
        authorFilmRepository.deleteAll(film.getAuthorFilm());
        filmRepository.delete(film);
    }

    @Override
    public List<FilmDomain> readAll() {
        return filmRepository.findAll().stream().map(filmDomainMapper::filmToDomain).collect(Collectors.toList());
    }
}
