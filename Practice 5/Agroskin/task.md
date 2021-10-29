# Задание 5: Агроскин Александр, БПИ197

# №1: 

1. Показать все названия книг вместе с именами издателей.
```sql
SELECT DISTINCT books.title, publishers.name
FROM books
         JOIN publishers ON books.publisher_name = publishers.name
```
2. В какой книге наибольшее количество страниц?

```sql
SELECT DISTINCT books.isbn
FROM books
ORDER BY books.page_count DESC
LIMIT 1
```

3. Какие авторы написали более 5 книг?

```sql
SELECT DISTINCT books.author
FROM books
GROUP BY books.author
HAVING COUNT(*) > 5
```

4. В каких книгах более чем в два раза больше страниц, чем среднее количество страниц для всех книг?

```sql
SELECT books.isbn
FROM books
WHERE (SELECT AVG(books.page_count) FROM books) < books.page_count * 0.5
```

5. Какие категории содержат подкатегории?

```sql
SELECT DISTINCT categories.name
FROM categories
WHERE categories.name IN (SELECT DISTINCT categories.parent_name FROM categories)
```

6. У какого автора (предположим, что имена авторов уникальны) написано максимальное количество книг?

```sql
SELECT author_counts.author
FROM (
         SELECT books.author, COUNT(books.isbn) AS author_count FROM books GROUP BY books.author
     ) author_counts
ORDER BY author_count DESC
LIMIT 1
```

7. Какие читатели забронировали все книги (не копии), написанные "Марком Твеном"?

```sql
SELECT readers.number
FROM readers
         JOIN bookings ON readers.number = bookings.reader_number
         JOIN books ON bookings.isbn = books.isbn
WHERE books.author = 'Марк Твен'
GROUP BY readers.number
HAVING COUNT(DISTINCT books.isbn) = (
    SELECT COUNT(*)
    FROM books
    WHERE author = 'Марк Твен'
)
```

8. Какие книги имеют более одной копии?

```sql
SELECT copies.isbn
FROM copies
GROUP BY copies.isbn
HAVING COUNT(*) > 1
```

9. ТОП 10 самых старых книг

```sql
SELECT books.isbn
FROM books
ORDER BY books.year
LIMIT 10
```

10. Перечислите все категории в категории “Спорт” (с любым уровнем вложености).

```sql
WITH RECURSIVE get_subcategory(cat) AS (
    SELECT categories.name
    FROM categories
    WHERE categories.parent_name = 'Спорт'
    UNION
    SELECT categories.name
    FROM categories
             JOIN get_subcategory res ON categories.name = res.cat
)
SELECT cat
FROM get_subcategory
```

# №2:

1. Добавьте запись о бронировании читателем ‘Василеем Петровым’ книги с ISBN 123456 и номером копии 4.

```sql
INSERT INTO bookings(reader_number, isbn, copy_number, return_date)
SELECT DISTINCT readers.number, '123456', 4, '2022-01-01'
FROM readers
WHERE readers.first_name = 'Василий'
  AND readers.last_name = 'Петров'
```

2. Удалить все книги, год публикации которых превышает 2000 год.

```sql
DELETE
FROM books
WHERE books.year > 2000
```

3. Измените дату возврата для всех книг категории "Базы данных", начиная с 01.01.2016, чтобы они были в заимствовании на 30 дней дольше (предположим, что в SQL можно добавлять числа к датам).

```sql
UPDATE bookings
SET bookings.return_date = bookings.return_date + 30
WHERE bookings.return_date > '2016-01-01'
  AND bookings.isbn IN (SELECT books.isbn
                        FROM books
                                 JOIN book_categories bc ON books.isbn = bc.isbn
                        WHERE bc.category_name = 'Базы данных')
```

# №3:

1. Запрос выбирает идентификатор и имя тех студентов, у которых оценка (если это означает Note) строго меньше 4.
2. Запрос выводит сумму кредитов по в сем лекциям каждого профессора (идентификатор, имя). Union нужен для того, чтобы выставить профессорам без лекций сумму 0.
3. Запрос выводит для каждого студента, у которого нету оценок, строго меньших 4, его максимальную оценку.
