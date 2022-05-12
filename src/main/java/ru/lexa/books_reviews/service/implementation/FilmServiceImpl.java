package ru.lexa.books_reviews.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lexa.books_reviews.exception.InputErrorException;
import ru.lexa.books_reviews.repository.FilmRepository;
import ru.lexa.books_reviews.repository.entity.Film;
import ru.lexa.books_reviews.service.FilmService;

import java.util.List;

@Service
@AllArgsConstructor
public class FilmServiceImpl implements FilmService {

	private FilmRepository filmRepository;

	@Override
	public Film create(Film film) {
		return filmRepository.save(film);
	}

	@Override
	public Film read(long id) {
		return filmRepository.findById(id)
				.orElseThrow(() -> new InputErrorException("Нет фильиа с данным id"));
	}

	@Override
	public Film update(Film film) {
		return filmRepository.save(film);
	}

	@Override
	public void delete(long id) {
		filmRepository.deleteById(id);
	}

	@Override
	public List<Film> readAll() {
		return filmRepository.findAll();
	}
}
