package ru.lexa.books_reviews.reviews.integration;

import controller.FilmController;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "films-client", url = "${books_films.url}")
public interface FilmClient extends FilmController {
}
