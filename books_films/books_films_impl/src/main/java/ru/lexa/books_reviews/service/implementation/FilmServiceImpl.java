package ru.lexa.books_reviews.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lexa.books_reviews.domain.FilmDomain;
import ru.lexa.books_reviews.exception.FilmNotFoundException;
import ru.lexa.books_reviews.exception.NameErrorException;
import ru.lexa.books_reviews.repository.FilmRepository;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.repository.mapper.FilmDomainMapper;
import ru.lexa.books_reviews.service.BookService;
import ru.lexa.books_reviews.service.FilmService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса {@link ru.lexa.books_reviews.service.FilmService}
 */
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    private final FilmDomainMapper filmDomainMapper;

    private final AmqpTemplate rabbitTemplate;

    private final BookService bookService;

    @Value("${spring.rabbitmq.routing-key.deleteFilmsReviews}")
    public String DELETE_FILM_REVIEW_ROUTING_KEY;
    @Value("${spring.rabbitmq.exchange}")
    public String EXCHANGE;


    @CacheEvict(value = "books", key = "#film.book.id")
    @Transactional
    @Override
    public FilmDomain create(FilmDomain film) {
        bookService.read(film.getBook().getId());
        try {
            film = filmDomainMapper.filmToDomain(filmRepository.save(filmDomainMapper.domainToFilm(film)));
        } catch (DataIntegrityViolationException e) {
            throw new NameErrorException();
        }
        return film;
    }

    @Cacheable(value = "films", key = "#id")
    @Override
    public FilmDomain read(long id) {
        return filmDomainMapper.filmToDomain(filmRepository.findById(id)
                .orElseThrow(() -> {
                    throw new FilmNotFoundException(id);
                }));
    }

    @Caching(
            put = @CachePut(value = "films", key = "#film.id"),
            evict = @CacheEvict(value = "books", key = "#film.book.id")
    )
    @Transactional
    @Override
    public FilmDomain update(FilmDomain film) {
        bookService.read(film.getBook().getId());
        try {
            film = filmDomainMapper.filmToDomain(filmRepository.saveAndFlush(filmDomainMapper.domainToFilm(film)));
        } catch (DataIntegrityViolationException e) {
            throw new NameErrorException();
        }
        return film;
    }

    @Caching(
            evict = {
                    @CacheEvict (value = "films", key = "#id"),
                    @CacheEvict(value = "books", key = "#film.book.id")
            }
    )
    @Transactional
    @Override
    public void delete(long id) {
        Film film = filmRepository.findById(id).orElseThrow(() -> {throw new FilmNotFoundException(id);});
        filmRepository.delete(film);
        rabbitTemplate.convertAndSend(EXCHANGE, DELETE_FILM_REVIEW_ROUTING_KEY, id);
    }

    @Override
    public List<FilmDomain> readAll() {
        return filmRepository.findAll().stream().map(filmDomainMapper::filmToDomain).collect(Collectors.toList());
    }
}
