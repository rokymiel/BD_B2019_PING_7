CREATE TABLE Reader(ID, LastName, FirstName, Address, BirthDate);
CREATE TABLE Book(ISBN, Title, Author, PagesNum, PubYear, PubName);
CREATE TABLE Publisher(PubName, PubAdress);
CREATE TABLE Category(CategoryName, ParentCat);
CREATE TABLE Copy(ISBN, CopyNumber, Shelf, Position);
CREATE TABLE Borrowing(ReaderNr, ISBN, CopyNumber, ReturnDate);
CREATE TABLE BookCat(ISBN, CategoryName);

INSERT INTO Book
VALUES ('9780345342966', 'Fahrenheit 451', 'Ray Bradbury', 179, '1987-01-01', 'Del Ray'),
('9780670240173', 'Dialogues with Marcel Duchamp', 'Pierre Cabanne', 224, '2019-01-01', 'GarageMCA'),
('9780321563842', 'The C++ Programming Language', 'Bjarne Stroustrup', 240, '2013-05-01', 'Addison–Wesley'),
('9781840227864', 'The Little Prince', 'Antoine de Saint-Exupery', 144, '2018-01-01', NULL),
('9780898865745', 'The Crystal Horizon: Everest-The First Solo Ascent', 'Reinhold Messner', 324, '1989-01-01',
'Mountaineers Books'),
('9783540765042', 'Plate Tectonics', 'Frisch', 324, '2011-01-01', 'Springer-Verlag Berlin Heidelberg'),
('9783540765043', 'Fake', 'Mark Twain', 1000, '2010-01-01', NULL),
('9783540765046', 'Fake2', 'Mark Twain', 1000, '2010-01-01', NULL),
('9783540765045', 'Fake C++', 'Bjarne Stroustrup', 1000, '2013-01-01', NULL);

INSERT INTO Publisher
VALUES ('Del Ray', 'Fiction'),
('Addison–Wesley', 'Reference'),
('GarageMCA', 'Scientific'),
('Mountaineers Books', 'Mountain Travel');

INSERT INTO categories(name, parent_name)
VALUES ('Mountain Travel', 'Travel'),
('Travel', NULL),
('Adventures', NULL),
('Space', NULL),
('Sport', NULL),
('Swimming', 'Sport'),
('Diving', 'Swimming'),
('Databases', NULL);

INSERT INTO BookCat
VALUES ('9780345342966', 'Science fiction'),
('9780345342966', 'Adventures'),
('9780321563842', 'True crime'),
('9780321563842', 'Detective'),
('9781840227864', 'Space'),
('9781840227864', 'Adventures'),
('9781840227864', 'Fantasy'),
('9781840227864', 'Travel'),
('9781840227864', 'Mountains'),
('9780898865745', 'Mountains'),
('9780898865745', 'Mountain Travel'),
('9783540765042', 'Mountains'),
('9783540765043', 'Databases'),
('9783540765046', 'Databases'),
('9783540765045', 'Databases');


INSERT INTO Reader
VALUES (0, 'Zubareva', 'Nataliia', 'Ulitsa Miklukho-Maklaya, 6, Moscow, 117198', '2001-05-04'),
(1, 'Koneva', 'Alexandra', 'Lomonosovskiy Prospekt, 27 корпус 4, Moscow, 119234', '2000-12-26'),
(2, 'Andreeva', 'Anna', 'Pokrovsky Blvd, 11, Moscow, 109028', '1989-08-09'),
(3, 'Sergeev', 'Vitaliy', 'Leninskiy Prospekt, 65, Moscow, 119991', '2004-11-18'),
(4, 'Ivanov', 'Ivan', 'Mochovaya street, 1, Moscow, 119991', '2004-12-08');


INSERT INTO Borrowing
VALUES (0, '9780345342966', 7, '2021-09-10'),
(0, '9780670240173', 3, '2021-10-01'),
(4, '9780345342966', 1, '2022-09-01'),
(1, '9780321563842', 5, '2019-10-02'),
(3, '9781840227864', 2, '2025-01-01'),
(3, '9780321563842', 4, '2025-01-10'),
(4, '9780670240173', 3, '2025-06-19'),
(4, '9780670240173', 9, '2025-10-01'),
(0, '9780670240173', 9, '2026-01-01'),
(0, '9783540765046', 1, '2011-01-01'),
(0, '9783540765043', 1, '2016-01-02'),
(1, '9783540765046', 1, '2026-01-01'),
(1, '9783540765046', 2, '2026-01-01'),
(1, '9783540765045', 2, '2026-01-01');

INSERT INTO Copy
VALUES ('9780670240173', '9', '1', '1'),
('9780670240173', '3', '1', '2'),
('9780321563842', '4', '1', '3'),
('9781840227864', '2', '2', '1'),
('9780321563842', '5', '1', '4'),
('9780345342966', '1', '2', '2'),
('9780345342966', '7', '2', '3'),
('9783540765043', '1', '2', '3'),
('9783540765046', '1', '2', '3'),
('9783540765046', '2', '2', '3');

.eqp on
.headers on
.mode column
