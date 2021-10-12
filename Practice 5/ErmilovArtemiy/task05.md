# Subtask 1
### Query 1.
Показать все названия книг вместе с именами издателей.
```sql
SELECT books.title, books.publisher_name
FROM books;
```

### Query 2.
В какой книге наибольшее количество страниц?
```sql
SELECT books.isbn
FROM books
ORDER BY books.page_count DESC
LIMIT 1;
```

### Query 3.
Какие авторы написали более 5 книг?
```sql
SELECT books.author
FROM books
GROUP BY books.author
HAVING COUNT(*) > 5;
```

### Query 4.
В каких книгах более чем в два раза больше страниц, чем среднее количество страниц для всех книг?
```sql
SELECT books.ISBN
FROM books
WHERE books.page_count > (SELECT 2 * AVG(books.page_count) FROM books);
```

### Query 5.
Какие категории содержат подкатегории?
```sql
SELECT DISTINCT categories.parent_name
FROM categories
WHERE parent_name IS NOT NULL;
```

### Query 6.
У какого автора (предположим, что имена авторов уникальны) написано максимальное количество книг?
```sql
SELECT Result.Author FROM (
	SELECT books.Author, COUNT(books.ISBN) AS BookCnt
	FROM books
	GROUP BY books.Author) Result
ORDER BY Result.BookCnt DESC
LIMIT 1;
```

### Query 7.
Какие читатели забронировали все книги (не копии), написанные "Марком Твеном"?
```sql
SELECT number
FROM (SELECT number, COUNT(*) AS DistinctNum
    FROM (SELECT DISTINCT *
          FROM bookings
          JOIN readers ON bookings.reader_number = readers.number
          JOIN books ON bookings.isbn = books.isbn
          WHERE books.author = 'Марк Твен') AS SubResult
          GROUP BY number) AS Result
WHERE DistinctNum = (SELECT COUNT(*) FROM books WHERE author = 'Марк Твен');
```

### Query 8.
Какие книги имеют более одной копии?
```sql
SELECT copies.isbn
FROM copies
GROUP BY copies.isbn
HAVING COUNT(*) > 1;
```

### Query 9.
ТОП 10 самых старых книг.
```sql
SELECT books.isbn
FROM books
ORDER BY books.year
LIMIT 10;
```

### Query 10.
Перечислите все категории в категории “Спорт” (с любым уровнем вложености).
```sql
WITH RECURSIVE RecCatSearch(CatName) AS (
    SELECT categories.name
    FROM categories
    WHERE categories.parent_name = 'Спорт'
    UNION
    SELECT categories.name
    FROM categories JOIN RecCatSearch ON categories.parent_name = RecCatSearch.CatName
) SELECT * FROM RecCatSearch;
```

# Subtask 2
### Query 1.
Добавьте запись о бронировании читателем ‘Василеем Петровым’ книги с ISBN 123456 и номером копии 4.
(**Допускаем, что такой пользователь один.**)
```sql
INSERT INTO bookings(reader_number, isbn, copy_number, return_date)
SELECT readers.number, '123456', 4, '2021-12-31' FROM readers
WHERE readers.first_name = 'Василий' AND readers.last_name = 'Петров';
```

### Query 2.
Удалить все книги, год публикации которых превышает 2000 год.
```sql
DELETE FROM books
WHERE books.year > 2000;
```

### Query 3.
Измените дату возврата для всех книг категории "Базы данных", начиная с 01.01.2016, чтобы они были в заимствовании на 30 дней дольше (предположим, что в SQL можно добавлять числа к датам).
```sql
UPDATE bookings
SET return_date = return_date + 30
WHERE ISBN IN (
    SELECT B.ISBN FROM book_categories
    INNER JOIN bookings B on book_categories.ISBN = B.ISBN
    WHERE category_name = 'Базы данных' AND B.return_date >= DATE('01.01.2016'))
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
Ответ: Выбрать имена и максимальные оценки студентов, еслми они не меньше 4.
