package ru.lexa.books_reviews.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lexa.books_reviews.domain.FilmDomain;
import ru.lexa.books_reviews.exception.FilmNotFoundException;
import ru.lexa.books_reviews.exception.NameErrorException;
import ru.lexa.books_reviews.repository.FilmRepository;
import ru.lexa.books_reviews.repository.mapper.FilmDomainMapper;
import ru.lexa.books_reviews.service.FilmService;

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

	@Transactional
	@Override
	public FilmDomain create(FilmDomain film) {
		try {
			return filmDomainMapper.filmToDomain(filmRepository.save(filmDomainMapper.domainToFilm(film)));
		} catch (DataIntegrityViolationException e) {
			throw new NameErrorException();
		}
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
		film.setReviews(read(film.getId()).getReviews());
		try {
			return filmDomainMapper.filmToDomain(filmRepository.save(filmDomainMapper.domainToFilm(film)));
		} catch (DataIntegrityViolationException e) {
			throw new NameErrorException();
		}
	}

	@Transactional
	@Override
	public void delete(long id) {
		filmRepository.deleteById(read(id).getId());
	}

	@Override
	public List<FilmDomain> readAll() {
		return filmRepository.findAll().stream().map(filmDomainMapper::filmToDomain).collect(Collectors.toList());
	}
}
