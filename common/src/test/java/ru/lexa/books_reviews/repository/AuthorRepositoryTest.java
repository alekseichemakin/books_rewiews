package ru.lexa.books_reviews.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts="classpath:/AuthorRepositoryTest.sql")
public class AuthorRepositoryTest {
    @Autowired
    AuthorRepository authorRepository;

    @Test
    public void whenGetAverageRating_thenReturnAverageRating() {
        //when
        Double rating = authorRepository.getAverageRating(1);

        //then
        assertEquals(rating, 6.5, 0.1);
    }
}
