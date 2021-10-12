# Задание №5. Борисов Костя БПИ197
## Задача 1
### Запрос 1
Показать все названия книг вместе с именами издателей.

```sql
SELECT title, publisher_name FROM Books;
```

### Запрос 2
В какой книге наибольшее количество страниц?

```sql
SELECT isbn FROM Books WHERE page_count = (SELECT max(page_count) FROM Books);
```

### Запрос 3
Какие авторы написали более 5 книг?

```sql
SELECT author FROM Books GROUP BY author HAVING count(*) > 5;
```

### Запрос 4
В каких книгах более чем в два раза больше страниц, чем среднее количество страниц для всех книг?

```sql
SELECT isbn FROM Books WHERE page_count > (SELECT avg(page_count) FROM Books)*2;
```

### Запрос 5
Какие категории содержат подкатегории?

```sql
SELECT name FROM Categories WHERE name IN (SELECT parent_name FROM Categories WHERE parent_name IS NOT NULL);
```

### Запрос 6
У какого автора (предположим, что имена авторов уникальны) написано максимальное количество книг?

```sql
SELECT author FROM Books GROUP BY author HAVING count(*) = (
  SELECT max(cnt) FROM (SELECT count(*) AS cnt FROM Books GROUP BY author) t
);
```

### Запрос 7
Какие читатели забронировали все книги (не копии), написанные "Марком Твеном"?

```sql
SELECT Readers.number FROM Readers
JOIN Bookings ON Bookings.reader_number = Readers.number
JOIN Books ON Books.isbn = Bookings.isbn
WHERE Books.author = 'Марк Твен'
GROUP BY Readers.number HAVING count(DISTINCT Books.isbn) = (
  SELECT count(*) FROM Books WHERE Books.author = 'Марк Твен'
);
```

### Запрос 8
Какие книги имеют более одной копии?

```sql
SELECT isbn FROM Copies GROUP BY isbn HAVING count(*) > 1;
```

### Запрос 9
ТОП 10 самых старых книг

```sql
SELECT isbn FROM Books ORDER BY year LIMIT 10;
```

### Запрос 10
Перечислите все категории в категории “Спорт” (с любым уровнем вложености).

```sql
WITH RECURSIVE r(c) AS (
  SELECT CAST('Sport' AS text)
  UNION
  SELECT name FROM Categories
  JOIN r ON r.c = parent_name
)
SELECT * FROM r WHERE c != 'Sport';
```

## Задача 2
### Запрос 1
Добавьте запись о бронировании читателем ‘Василеем Петровым’ книги с ISBN 123456 и номером копии 4.

```sql
INSERT INTO Bookings(reader_number, isbn, copy_number, return_date)
SELECT number, '123456', 4, now() + '10 day' FROM Readers
WHERE first_name = 'Василий' AND last_name = 'Петров';
```

Дополнительные запросы чтобы небыло ошибок про FOREIGN KEY:
```sql
INSERT INTO Books VALUES ('123456', 'Арифметические последовательности', 1269, 'Аристотель', 123, 'Schultz LLC');
INSERT INTO Copies VALUES (4, '123456', 1);
```

### Запрос 2
Удалить все книги, год публикации которых превышает 2000 год.

```sql
DELETE FROM Books WHERE year > 2000;
```

### Запрос 3
Измените дату возврата для всех книг категории "Базы данных", начиная с 01.01.2016,
чтобы они были в заимствовании на 30 дней дольше (предположим, что в SQL можно добавлять числа к датам).

```sql
UPDATE Bookings
SET return_date = return_date + 30
WHERE return_date > '2016-01-01' AND isbn IN (
  SELECT isbn FROM book_categories WHERE category_name = 'Базы данных'
);
```

## Задача 3
### Запрос 1
Находим всех студентов у которых нету неодной оценки больше или равно 4.0, тосеть все незачи.

### Запрос 2
Для всех профессоров находим сумму кредитов всех их лекций, или 0 если лекций нет.

### Запрос 3
Для каждого студента ищем его максимальную оценку, если она не незач.
