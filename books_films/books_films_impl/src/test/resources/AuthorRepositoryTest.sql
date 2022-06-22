insert into author (id, name) values (1, 'testAuthor');
insert into book (id, name) values (1, 'testBook');
insert into book (id, name) values (2, 'testBook1');
insert into book_author(book_id, author_id) values (1, 1);
insert into book_author(book_id, author_id) values (2, 1);
insert into review (id, book_id, rating) values (1, 1, 6);
insert into review (id, book_id, rating) values (2, 1, 5);
insert into review (id, book_id, rating) values (3, 2, 7);
insert into review (id, book_id, rating) values (4, 2, 8);