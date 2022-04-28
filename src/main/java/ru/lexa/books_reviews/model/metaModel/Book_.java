package ru.lexa.books_reviews.model.metaModel;

import ru.lexa.books_reviews.model.Book;
import ru.lexa.books_reviews.model.Review;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Book.class)
public abstract class Book_ {

	public static volatile CollectionAttribute<Book, Review> review;
	public static volatile SingularAttribute<Book, String> author;
	public static volatile SingularAttribute<Book, String> name;
	public static volatile SingularAttribute<Book, String> description;
	public static volatile SingularAttribute<Book, Long> id;

	public static final String REVIEW = "review";
	public static final String AUTHOR = "author";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";

}

