package ru.lexa.books_reviews.model.metaModel;

import ru.lexa.books_reviews.model.Book;
import ru.lexa.books_reviews.model.Review;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Review.class)
public abstract class Review_ {

	public static volatile SingularAttribute<Review, Book> book;
	public static volatile SingularAttribute<Review, Integer> rating;
	public static volatile SingularAttribute<Review, Long> id;
	public static volatile SingularAttribute<Review, String> text;

	public static final String BOOK = "book";
	public static final String RATING = "rating";
	public static final String ID = "id";
	public static final String TEXT = "text";

}

