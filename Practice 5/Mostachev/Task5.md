# Домашнее задание 5, Мостачев Андрей, БПИ197

Тут все сделано на постгре (я не уверен, что это имеет какое-то значение, но все же).

## Задача 1

1. 

```sql
SELECT title, publisher_name FROM books;
```

2. 

```sql
SELECT title, isbn from books WHERE page_count = (SELECT max(page_count) FROM books);
```

3. 

```sql
SELECT author FROM books GROUP BY author HAVING count(*) > 5;
```

4. 

```sql
SELECT title, isbn from books WHERE page_count > (SELECT avg(page_count) FROM books) * 2;
```

5. 

```sql
SELECT name FROM categories
WHERE name IN (SELECT parent_name FROM categories WHERE parent_name IS NOT NULL );
```

6. 

```sql
SELECT author FROM books GROUP BY author
HAVING count(*) = (SELECT max(cnt) FROM (SELECT count(*) AS cnt FROM books GROUP BY author) AS HELP);
```

7. 

```sql
SELECT number FROM readers
JOIN bookings b ON readers.number = b.reader_number
JOIN books b2 ON b2.isbn = b.isbn
WHERE author = 'Марк Твен'
GROUP BY number HAVING count(DISTINCT b2.isbn) =
                       (SELECT count(*) FROM books WHERE author = 'Марк Твен');
```

8. 

```sql
SELECT isbn FROM copies GROUP BY isbn HAVING count(*) > 1;
```

9. 
```sql
SELECT isbn FROM books ORDER BY year LIMIT 10;
```

10. Это я просто скатал у одногруппников. Я и в других пунктах сравнивал, если что, но тут я бы сам прям не догадался, как это сделать, так что не засчитывайте, наверно...

Я, в целом, разобрался, что как в этом коде, так что сейчас все понятно

```sql
WITH RECURSIVE sub(category) AS (
    SELECT categories.name FROM categories WHERE categories.parent_name = 'Sport'
    UNION
    SELECT categories.name FROM categories
        JOIN sub res ON categories.name = res.category
)
SELECT category FROM sub;
```

## Задача 2

1. 

```sql
INSERT INTO Bookings(reader_number, isbn, copy_number, return_date)
SELECT number, '123456', 4, '2021-10-20' FROM Readers
WHERE first_name = 'Василий' AND last_name = 'Петров';
```

2. 

```sql
DELETE FROM books WHERE books.year > 2000;
```

3. 

```sql
UPDATE bookings SET return_date = return_date + 30
WHERE return_date > '2016-01-01' AND isbn IN (
    SELECT isbn FROM book_categories WHERE category_name = 'Базы данных'
);
```

## Задача 3

1. Найти всех студентов, у которых нет оценок >= 4

2. У каждого профессора найти сумму кредитов. Если он не ведет лекций - 0.

3. Найти максимальную оценку каждого студента, если она >= 4.
