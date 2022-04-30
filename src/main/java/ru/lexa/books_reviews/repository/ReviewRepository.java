package ru.lexa.books_reviews.repository;

import org.springframework.data.repository.CrudRepository;
import ru.lexa.books_reviews.repository.entity.Review;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    @Override
    List<Review> findAll();
}
