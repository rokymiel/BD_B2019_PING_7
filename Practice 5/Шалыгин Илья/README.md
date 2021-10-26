Задача 1

Reader( ID, LastName, FirstName, Address, BirthDate)

Book ( ISBN, Title, Author, PagesNum, PubYear, PubName)

Publisher ( PubName, PubAdress)

Category ( CategoryName, ParentCat)

Copy ( ISBN, CopyNumber,, ShelfPosition)

Borrowing ( ReaderNr, ISBN, CopyNumber, ReturnDate)

BookCat ( ISBN, CategoryName )

Показать все названия книг вместе с именами издателей.
```sql
SELECT title, publisher_name FROM books;
```

В какой книге наибольшее количество страниц?
```sql
SELECT title FROM books
ORDER BY page_count DESC
LIMIT 1;
```

Какие авторы написали более 5 книг?
```sql
SELECT author FROM books
GROUP BY author
HAVING COUNT(*) > 5;
```

В каких книгах более чем в два раза больше страниц, чем среднее количество страниц для всех книг?
```sql
SELECT title FROM books
WHERE page_count > (SELECT AVG(page_count) FROM books) * 2;
```

Какие категории содержат подкатегории?
```sql
SELECT name
FROM categories AS parent
WHERE (
          SELECT COUNT(*)
          FROM categories AS child
          WHERE parent.name = child.parent_name
      ) > 0;
```

У какого автора (предположим, что имена авторов уникальны) написано максимальное количество книг?
```sql
SELECT author, COUNT(*) FROM books
GROUP BY author
ORDER BY COUNT(*) DESC 
LIMIT 1;
```

Какие читатели забронировали все книги (не копии), написанные "Марком Твеном"?
```sql
-
```

Какие книги имеют более одной копии?
```sql
SELECT title
FROM books as original
WHERE (
          SELECT COUNT(*)
          FROM copies AS copy
          WHERE copy.isbn = original.isbn
      ) > 1;
```

ТОП 10 самых старых книг
```sql
SELECT title, year
FROM books
ORDER BY year
LIMIT 10;
```

Перечислите все категории в категории “Спорт” (с любым уровнем вложености).
```sql
-
```

Задача 2
Напишите SQL-запросы для следующих действий:

Добавьте запись о бронировании читателем ‘Василеем Петровым’ книги с ISBN 123456 и номером копии 4.
```sql
INSERT INTO bookings (reader_number, copy_number, isbn, return_date)
VALUES (1, 4, 123456, '2021-10-12');
```

Удалить все книги, год публикации которых превышает 2000 год.
```sql
DELETE FROM books
WHERE year > 2000;
```
Измените дату возврата для всех книг категории "Базы данных", начиная с 01.01.2016, чтобы они были в заимствовании на 30 дней дольше (предположим, что в SQL можно добавлять числа к датам).
```sql
UPDATE bookings
SET return_date = return_date + 30
WHERE isbn IN (
    SELECT books.isbn
    FROM books
             INNER JOIN book_categories bc ON books.isbn = bc.isbn
    WHERE bc.category_name LIKE 'Базы данных'
);
```

Задача 3
1)Найти всех студентов у которых все оценки < 4
2)
3)Найти студента у которого есть оценка >= 4 и >= всех его оценок

