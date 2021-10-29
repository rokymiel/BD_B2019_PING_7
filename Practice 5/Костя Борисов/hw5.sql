-- это решение на sqlite-е

CREATE TABLE Reader(ID, LastName, FirstName, Address, BirthDate);
CREATE TABLE Book(ISBN, Title, Author, PagesNum, PubYear, PubName);
CREATE TABLE Publisher(PubName, PubAdress);
CREATE TABLE Category(CategoryName, ParentCat);
CREATE TABLE Copy(ISBN, CopyNumber, ShelfPosition);
CREATE TABLE Borrowing(ReaderNr, ISBN, CopyNumber, ReturnDate);
CREATE TABLE BookCat(ISBN, CategoryName);

-- Показать все названия книг вместе с именами издателей.
SELECT Title, PubName FROM Book;

-- В какой книге наибольшее количество страниц?
SELECT ISBN FROM Book WHERE PagesNum = (SELECT max(PagesNum) FROM Book);

-- Какие авторы написали более 5 книг?
SELECT Author FROM Book GROUP BY Author HAVING count(*) > 5;

-- В каких книгах более чем в два раза больше страниц, чем среднее количество страниц для всех книг?
SELECT ISBN FROM Book WHERE PagesNum > (SELECT avg(PagesNum) FROM Book)*2;

-- Какие категории содержат подкатегории?
SELECT CategoryName FROM Category WHERE CategoryName IN (SELECT ParentCat FROM Category WHERE ParentCat IS NOT NULL);

-- У какого автора (предположим, что имена авторов уникальны) написано максимальное количество книг?
SELECT Author FROM Book GROUP BY Author HAVING count(*) = (
  SELECT max(cnt) FROM (SELECT count(*) AS cnt FROM Book GROUP BY Author)
);

-- Какие читатели забронировали все книги (не копии), написанные "Марком Твеном"?
SELECT Reader.ID FROM Reader
JOIN Borrowing ON Borrowing.ReaderNr = Reader.ID
JOIN Book ON Book.ISBN = Borrowing.ISBN
WHERE Book.Author = 'Mark Twain'
GROUP BY Reader.ID HAVING count(DISTINCT Book.ISBN) = (
  SELECT count(*) FROM Book WHERE Book.Author = 'Mark Twain'
);

-- Какие книги имеют более одной копии?
SELECT ISBN FROM Copy GROUP BY ISBN HAVING count(*) > 1;

-- ТОП 10 самых старых книг
SELECT ISBN FROM Book ORDER BY PubYear LIMIT 10;

-- Перечислите все категории в категории “Спорт” (с любым уровнем вложености).
WITH RECURSIVE r(c) AS (
  SELECT 'Sport'
  UNION
  SELECT CategoryName FROM Category
  JOIN r ON r.c = ParentCat
)
SELECT * FROM r WHERE c != 'Sport';

-- Добавьте запись о бронировании читателем ‘Василеем Петровым’ книги с ISBN 123456 и номером копии 4.
INSERT INTO Borrowing(ReaderNr, ISBN, CopyNumber)
SELECT ID, '123456', 4 FROM Reader
WHERE FirstName = 'John' AND LastName = 'Johnson';

-- Удалить все книги, год публикации которых превышает 2000 год.
DELETE FROM Book WHERE PubYear > 2000;

-- Измените дату возврата для всех книг категории "Базы данных", начиная с 01.01.2016,
-- чтобы они были в заимствовании на 30 дней дольше (предположим, что в SQL можно добавлять числа к датам).
UPDATE Borrowing
SET ReturnDate = ReturnDate+30 -- попахивает синтаксической ошибкой
WHERE ReturnDate > '2016-01-01' AND ISBN IN (
  SELECT ISBN FROM BookCat WHERE CategoryName = 'Databases'
);
