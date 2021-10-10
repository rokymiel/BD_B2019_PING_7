# Subtask 1
### Query 1.
Показать все названия книг вместе с именами издателей.
```sql
SELECT Book.Title, Book.PubName
FROM Book;
```

### Query 2.
В какой книге наибольшее количество страниц?
```sql
SELECT Book.ISBN
FROM Book 
ORDER BY Book.PagesNum DESC
LIMIT 1;
```

### Query 3.
Какие авторы написали более 5 книг?
```sql
SELECT Book.Author
FROM Book
GROUP BY Book.Author
HAVING COUNT(*) > 5;
```

### Query 4.
В каких книгах более чем в два раза больше страниц, чем среднее количество страниц для всех книг?
```sql
SELECT Book.ISBN
FROM Book
WHERE Book.PagesNum > (SELECT 2 * AVG(Book.PagesNum) FROM Book);
```

### Query 5.
Какие категории содержат подкатегории?
```sql
SELECT C1.CategoryName
FROM Category C1 INNER JOIN Category C2 ON C1.CategoryName = C2.ParentCat;
```

### Query 6.
У какого автора (предположим, что имена авторов уникальны) написано максимальное количество книг?
```sql
SELECT Result.Author FROM (
	SELECT Book.Author, COUNT(Book.ISBN) AS BookCnt
	FROM Book
	GROUP BY Book.Author) Result
ORDER BY Result.BookCnt DESC
LIMIT 1;
```

### Query 7.
Какие читатели забронировали все книги (не копии), написанные "Марком Твеном"?
```sql
SELECT Borrowing.ReaderNr
FROM Borrowing INNER JOIN Book ON Borrowing.ISBN = Book.ISBN
WHERE Book.Author = 'Марк Твен'
GROUP BY Borrowing.ReaderNr HAVING COUNT(*) = (
	SELECT COUNT(*) FROM Book WHERE Book.Author = 'Марк Твен'
);
```

### Query 8.
Какие книги имеют более одной копии?
```sql
SELECT Copy.ISBN
FROM Copy
GROUP BY Copy.ISBN
HAVING COUNT(*) > 1;
```

### Query 9.
ТОП 10 самых старых книг.
```sql
SELECT Book.ISBN
FROM Book
ORDER BY Book.PubYear
LIMIT 10;
```

### Query 10.
Перечислите все категории в категории “Спорт” (с любым уровнем вложености).
```sql
WITH RECURSIVE RecCatSearch(CatName) AS (
    SELECT Category.CategoryName
    FROM Category
    WHERE Category.ParentCat = 'Спорт'
    UNION
    SELECT Category.CategoryName
    FROM Category JOIN RecCatSearch ON Category.ParentCat = RecCatSearch.CatName
) SELECT * FROM RecCatSearch;
```

# Subtask 2
### Query 1.
Добавьте запись о бронировании читателем ‘Василеем Петровым’ книги с ISBN 123456 и номером копии 4.
```sql
INSERT INTO Borrowing(ReaderNr, ISBN, CopyNumber)
SELECT Reader.ID, '123456', 4 FROM Reader
WHERE Reader.FirstName = 'Василий' AND Reader.LastName = 'Петров';
```

### Query 2.
Удалить все книги, год публикации которых превышает 2000 год.
```sql
DELETE FROM Book
WHERE Book.PubYear > 2000;
```

### Query 3.
Измените дату возврата для всех книг категории "Базы данных", начиная с 01.01.2016, чтобы они были в заимствовании на 30 дней дольше (предположим, что в SQL можно добавлять числа к датам).
```sql
UPDATE Borrowing
SET ReturnDate = ReturnDate + 30
WHERE ReturnDate > '2016.01.01';
```

# Subtask 3
### Query 1.
```sql
SELECT s.Name, s.MatrNr FROM Student s 
  WHERE NOT EXISTS ( 
    SELECT * FROM Check c WHERE c.MatrNr = s.MatrNr AND c.Note >= 4.0 ) ; 
```
Ответ: Выбрать всех студентов (имена и ID), у которых нет ни одной работы, написанной не менее, чем на 4.0.

### Query 2.
```sql
( SELECT p.ProfNr, p.Name, sum(lec.Credit) 
FROM Professor p, Lecture lec 
WHERE p.ProfNr = lec.ProfNr
GROUP BY p.ProfNr, p.Name)
UNION
( SELECT p.ProfNr, p.Name, 0 
FROM Professor p
WHERE NOT EXISTS ( 
  SELECT * FROM Lecture lec WHERE lec.ProfNr = p.ProfNr )); 
```
Ответ: Выбрать всех профессоров и сумму кредитов лекций, которые они ведут. Если лектор не ведет ни одной лекции, сумма кредитов считается равной нулю.

### Query 3.
```sql
SELECT s.Name, p.Note
  FROM Student s, Lecture lec, Check c
  WHERE s.MatrNr = c.MatrNr AND lec.LectNr = c.LectNr AND c.Note >= 4 
    AND c.Note >= ALL ( 
      SELECT c1.Note FROM Check c1 WHERE c1.MatrNr = c.MatrNr ) 
```
Ответ: Выбрать имена студентов и их макисмальные оценки, которые не меньше 4.0.
