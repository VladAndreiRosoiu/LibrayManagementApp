CREATE SCHEMA libraryDB;
USE libraryDB;

CREATE TABLE users(
id INT PRIMARY KEY AUTO_INCREMENT,
first_name VARCHAR(255) NOT NULL,
last_name VARCHAR(255) NOT NULL,
username VARCHAR(255) NOT NULL UNIQUE,
user_password VARCHAR(255) NOT NULL,
user_type ENUM('CLIENT', 'LIBRARIAN'),
email VARCHAR(255) NOT NULL UNIQUE,
registered_on DATE NOT NULL,
is_active BOOLEAN DEFAULT TRUE);

CREATE TABLE authors(
id INT PRIMARY KEY AUTO_INCREMENT,
first_name VARCHAR(255) NOT NULL,
last_name VARCHAR(255) NOT NULL,
additional_info VARCHAR(1000),
birth_date DATE NOT NULL,
CONSTRAINT author_cons UNIQUE (first_name, last_name, birth_date));

CREATE TABLE genre(
id INT PRIMARY KEY AUTO_INCREMENT,
genre_type VARCHAR (255) NOT NULL UNIQUE);

CREATE TABLE books(
id INT PRIMARY KEY AUTO_INCREMENT,
book_name VARCHAR(255) NOT NULL,
isbn BIGINT UNIQUE,
stock INT NOT NULL,
release_date DATE NOT NULL);

CREATE TABLE book_author(
id INT PRIMARY KEY AUTO_INCREMENT,
id_book INT NOT NULL,
id_author INT NOT NULL,
FOREIGN KEY (id_book) REFERENCES books(id),
FOREIGN KEY (id_author) REFERENCES authors(id),
CONSTRAINT book_author_constr UNIQUE (id_book, id_author));

CREATE TABLE book_genre(
id INT PRIMARY KEY AUTO_INCREMENT,
id_book INT NOT NULL,
id_genre INT NOT NULL,
CONSTRAINT book_genre_constr UNIQUE (id_book, id_genre),
FOREIGN KEY (id_book) REFERENCES books(id),
FOREIGN KEY (id_genre) REFERENCES genre(id));

CREATE TABLE borrowed_book_user(
id INT PRIMARY KEY AUTO_INCREMENT,
id_user INT NOT NULL,
id_book INT NOT NULL,
borrowed_on DATE NOT NULL,
returned_on DATE,
CONSTRAINT borr_constr UNIQUE (id_user, id_book, returned_on),
FOREIGN KEY (id_user) REFERENCES users(id),
FOREIGN KEY (id_book) REFERENCES books(id)
);

INSERT INTO libraryDB.authors(first_name, last_name, additional_info, birth_date )
VALUES
('Naomi', 'Novik' , 'Naomi Novik is an American author of speculative fiction. Novik won both the Nebula Award for Best Novel and the Mythopoeic Fantasy Award in 2016 for her novel Uprooted.', '1973-04-30'),
('Roshani', 'Chokshi', 'Roshani Chokshi is an American children\' book author and a New York Times bestselling author.' , '1991-02-14'),
('Anthony', 'Doerr','Anthony Doerr is an American author of novels and short stories. He gained widespread recognition for his 2014 novel All the Light We Cannot See, which won the Pulitzer Prize for Fiction. ' ,'1973-10-27'),
('Markus', 'Zusak' , 'Markus Zusak is an Australian writer of German origin. He is best known for The Book Thief and The Messenger, two novels which became international bestsellers. He won the Margaret A. Edwards Award in 2014.', '1975-06-23'),
('Jane', 'Austen', 'Jane Austen was an English novelist known primarily for her six major novels, which interpret, critique and comment upon the British landed gentry at the end of the 18th century. Austen\'s plots often explore the dependence of women on marriage in the pursuit of favourable social standing and economic security.', '1775-12-16'),
('Anna', 'Quindlen', 'Anna Marie Quindlen is an American author, journalist, and opinion columnist. Her New York Times column, Public and Private, won the Pulitzer Prize for Commentary in 1992. She began her journalism career in 1974 as a reporter for the New York Post. Between 1977 and 1994 she held several posts at The New York Times.', '1953-07-8'),
('Delia', 'Owens', 'Delia Owens is an American author and zoologist. Her debut novel Where the Crawdads Sing topped The New York Times Fiction Best Sellers of 2019 for 25 non-consecutive weeks. The book has been on New York Times Bestsellers lists for more than a year. ', '1949-04-4'),
('Alex', 'Michaelides', 'Alex Michaelides is a bestselling British-Cypriot author and screenwriter. His debut novel, the psychological thriller The Silent Patient, is a New York Times and Sunday Times besteller.', '1977-09-04'),
('George', 'Orwell', 'Eric Arthur Blair, known by his pen name George Orwell, was an English novelist, essayist, journalist and critic. His work is characterised by lucid prose, biting social criticism, opposition to totalitarianism, and outspoken support of democratic socialism.', '1903-06-25'),
('Thomas', 'Pynchon', 'Thomas Ruggles Pynchon Jr. is an American novelist. A MacArthur Fellow, he is noted for his dense and complex novels. His fiction and non-fiction writings encompass a vast array of subject matter, genres and themes, including history, music, science, and mathematics. ', '1937-05-8'),
('Herman', 'Melville', 'Herman Melville was an American novelist, short story writer, and poet of the American Renaissance period. Among his best-known works are Moby-Dick, Typee, a romanticized account of his experiences in Polynesia, and Billy Budd, Sailor, a posthumously published novella.', '1819-08-01'),
('Andrew', 'Delbanco', 'Andrew H. Delbanco is the Alexander Hamilton Professor of American Studies at Columbia University. He is the author of several books, including College: What It Was, Is, and Should Be, which has been translated into Chinese, Korean, Turkish, Russian, and Hebrew. ', '1952-02-20'),
('Rockwell', 'Kent', 'Rockwell Kent was an American painter, printmaker, illustrator, writer, sailor, adventurer and voyager.', '1882-06-21');

INSERT INTO libraryDB.books(book_name, isbn, stock, release_date)
VALUES
('A Deadly Education', 9780593128480, 10, '2020-09-29'),
('The Silvered Serpents', 9781250144577, 5, '2020-09-22'),
('All the Light We Cannot See', 9781476746586, 30, '2014-05-01'),
('The Book Thief', 9780375831003, 13, '2006-03-14'),
('Pride and Prejudice', 9780679783268, 50, '2000-10-10'),
('Where the Crawdads Sing', 9780735219113, 3, '2018-08-14'),
('The Silent Patient', 9781250301697, 8, '2019-02-05'),
('Nineteen Eighty-Four', 9780452284234, 100, '2003-05-06'),
('Moby-Dick or, the Whale', 9780142437247, 66, '2003-02-21');

INSERT INTO libraryDB.book_author (id_book, id_author)
VALUES
(1,1),
(2,2),
(3,3),
(4,4),
(4,5),
(5,6),
(6,7),
(7,7),
(7,8),
(8,9),
(8,10),
(8,11);

INSERT INTO libraryDB.genre (genre_type)
VALUES
('Science Fiction'),
('Magical Realism'),
('High Fantasy'),
('Fantasy Fiction'),
('Contemporary Fantasy'),
('Young Adult Fiction'),
('Novel'),
('Historical Fiction'),
('Historical Novel'),
('Romance Novel'),
('Satire'),
('Novel of Manners'),
('Regency Romance'),
('Domestic Fiction'),
('Regency Fiction'),
('Bildungsroman'),
('Mystery'),
('Thriller'),
('Suspense'),
('Psychological Fiction'),
('Social Science Fiction'),
('Political Fiction'),
('Epic'),
('Adventure Fiction'),
('Nautical Fiction');

INSERT INTO libraryDB.book_genre (id_book, id_genre)
VALUES
(1,1),
(1,2),
(1,3),
(1,4),
(1,5),
(2,4),
(2,6),
(3,7),
(3,8),
(4,9),
(5,8),
(5,10),
(5,11),
(5,12),
(5,13),
(5,14),
(5,15),
(6,16),
(6,17),
(6,7),
(7,18),
(7,19),
(7,20),
(7,7),
(7,17),
(8,21),
(8,1),
(9,22),
(9,23),
(9,24),
(9,7);

INSERT INTO libraryDB.users(first_name, last_name, username, user_password, user_type, email, registered_on)
VALUES
('Vlad', 'Rosoiu', 'vladrosoiu', '118118&v^118$v;114114@r(114%r)111111&o^111$o;115115@s(115%s)111111&o^111$o;105105@i(105%i)117117&u^117$u;', 'CLIENT', 'vrosoiu@mail.com', '2018-04-12'),
('Andrei', 'Andreescu', 'aandreescu', '9797&a^97$a;110110@n(110%n)100100&d^100$d;114114@r(114%r)101101&e^101$e;105105@i(105%i)', 'LIBRARIAN', 'aandreescu@othermail.com', '2007-08-30'),
('Young', 'Mayer', 'youngmayer', '112112&p^112$p;9797@a(97%a)115115&s^115$s;115115@s(115%s)119119&w^119$w;111111@o(111%o)114114&r^114$r;100100@d(100%d)5151&3^51$3;', 'CLIENT','mayer@ymail.com', '2020-08-01'),
('Edmund', 'Kozey', 'edkozey', '112112&p^112$p;9797@a(97%a)115115&s^115$s;115115@s(115%s)119119&w^119$w;111111@o(111%o)114114&r^114$r;100100@d(100%d)5252&4^52$4;', 'CLIENT', 'ed@gmail.com', '2014-09-03');

INSERT INTO libraryDB.borrowed_book_user (id_user, id_book, borrowed_on, returned_on)
VALUES
(1,2,'2017-10-01','2017-10-14'),
(1,9,'2017-11-02','2017-11-30'),
(3,3,'2018-05-01','2018-05-30'),
(4,4,'2019-01-05','2019-01-28'),
(4,8,'2020-03-15','2020-04-01'),
(1,2,'2020-04-20','2020-05-05'),
(3,7,'2020-08-01','2020-08-15');

INSERT INTO libraryDB.borrowed_book_user (id_user, id_book, borrowed_on)
VALUES
(1, 6, '2020-10-01'),
(3, 4, '2020-10-10');

UPDATE libraryDB.books
SET stock = (stock-1)
WHERE id=6;

UPDATE libraryDB.books
SET stock = (stock-1)
WHERE id=4;