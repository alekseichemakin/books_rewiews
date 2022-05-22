insert into book (id, name) values (1, 'testBook');
insert into film (id, book_id, name) values (1, 1, 'testFilm');
insert into review (id, book_id, rating) values (1, 1, 6);
insert into review (id, book_id, rating) values (2, 1, 5);
insert into review (id, film_id, rating) values (3, 1, 5);