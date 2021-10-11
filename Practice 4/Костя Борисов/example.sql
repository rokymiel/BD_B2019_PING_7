CREATE TABLE Reader(ID, LastName, FirstName, Address, BirthDate);
CREATE TABLE Book(ISBN, Title, Author, PagesNum, PubYear, PubName);
CREATE TABLE Publisher(PubName, PubAdress);
CREATE TABLE Category(CategoryName, ParentCat);
CREATE TABLE Copy(ISBN, CopyNumber, ShelfPosition);

CREATE TABLE Borrowing(ReaderNr, ISBN, CopyNumber, ReturnDate);
CREATE TABLE BookCat(ISBN, CategoryName);

INSERT INTO Book VALUES
('978-3-991-12032-2', 'Страна Слепых', 'Герберт Уэллс', 156, '1911-01-01', NULL),
('978-5-346-03492-6', 'Горы. От возникновения до разрушения', 'Лев Тарасов', 175, '2016-01-01', 'Мнемозина'),
('978-0-345-34296-6', 'Fahrenheit 451', 'Ray Bradbury', 179, '1987-01-01', NULL),
('978-0-670-24017-3', 'Dialogues with Marcel Duchamp', 'Pierre Cabanne', 224, '2019-01-01', NULL),
('978-0-321-56384-2', 'The C++ Programming Language', 'Bjarne Stroustrup', 240, '2013-05-01', NULL),
('978-1-840-22786-4', 'The Little Prince', 'Antoine de Saint-Exupery', 144, '2018-01-01', NULL);

INSERT INTO BookCat VALUES
('978-3-991-12032-2', 'Горы'),
('978-3-991-12032-2', 'Путешествия'),
('978-5-346-03492-6', 'Горы'),
('978-5-346-03492-6', 'Учебник'),
('978-5-346-03492-6', 'Геология');

INSERT INTO Reader VALUES
(0, 'Иванов', 'Иван', 'пр. Космонавтов, 14, Санкт-Петербург, 196105', '1970-01-01'),
(1, 'Зубарева', 'Наталия', 'Калашный пер., 6, Москва, 125009', '2001-05-04'),
(2, 'Кочарян', 'Тигран', 'Оборонная ул., 22, Санкт-Петербург, 198099', '2000-12-26'),
(3, 'Ризоева', 'Амина', 'ул. Герцена, 1, Павловский Посад, Московская обл., 142500', '1989-08-09'),
(4, 'Скрыпников', 'Егор', 'Калеганова ул., 5, Волгоград, Волгоградская обл., 400081', '2004-11-18');

INSERT INTO Borrowing VALUES
(0, '978-3-991-12032-2', 7, '2021-09-10'),
(0, '978-0-345-34296-6', 3, '2021-10-01'),
(4, '978-0-321-56384-2', 1, '2022-09-01'),
(1, '978-0-670-24017-3', 5, '2019-10-02'),
(3, '978-0-345-34296-6', 2, '2025-01-01'),
(3, '978-0-321-56384-2', 2, '2025-01-01'),
(3, '978-0-670-24017-3', 2, '2025-01-01');

CREATE TABLE City(Name, Region);
CREATE TABLE Station(Name, ♯Tracks, CityName, Region);
CREATE TABLE Train(TrainNr, Length, StartStationName, EndStationName);
CREATE TABLE Connection(FromStation, ToStation, TrainNr, Departure, Arrival);

.eqp on
.headers on
.mode column
