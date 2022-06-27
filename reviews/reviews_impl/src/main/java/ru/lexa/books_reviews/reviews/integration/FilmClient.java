package ru.lexa.books_reviews.reviews.integration;

import controller.FilmController;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "films-client", url = "http://localhost:8080")
public interface FilmClient extends FilmController {
}
