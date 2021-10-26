# Задание №5. Кочарян Тигран БПИ197
# Задача 1
### Запрос 1.
**Показать все названия книг вместе с именами издателей.**

```sql
SELECT title, publisher_name FROM Books; 
```

### Запрос 2.
**В какой книге наибольшее количество страниц?**

```sql
SELECT isbn FROM Books
WHERE page_count = (SELECT max(page_count) FROM Books); 
```

### Запрос 3.
**Какие авторы написали более 5 книг?**

```sql
SELECT author FROM Books 
GROUP BY author 
  HAVING COUNT(*) > 5;
```

### Запрос 4.
**В каких книгах более чем в два раза больше страниц, чем среднее количество страниц для всех книг?**

```sql
SELECT isbn FROM Books 
WHERE page_count > (SELECT 2 * AVG(page_count) FROM Books)*2;
```

### Запрос 5.
**Какие категории содержат подкатегории?**

```sql
SELECT name FROM Categories 
WHERE name  
  IN (SELECT DISTINCT parent_name FROM Categories
        WHERE parent_name IS NOT NULL);
```

### Запрос 6.
**У какого автора (предположим, что имена авторов уникальны) написано максимальное количество книг?**

```sql
SELECT author FROM Books 
GROUP BY author 
  HAVING count(*) = 
    (SELECT max(count_temp) FROM (SELECT count(*) AS count_temp 
       FROM Books GROUP BY author) t);
```

### Запрос 7.
**Какие читатели забронировали все книги (не копии), написанные "Марком Твеном"?**

```sql
SELECT Readers.number 
  FROM Readers 
JOIN Bookings
  ON Bookings.reader_number = Readers.number
JOIN Books 
  ON Books.ISBN = Bookings.ISBN
WHERE Books.Author = 'Марк Твен'
GROUP BY Readers.number
  HAVING COUNT(DISCTINCT Books.isbn) = (SELECT COUNT(*) FROM Books WHERE Books.author = 'Марк Твен');
```

### Запрос 8.
**Какие книги имеют более одной копии?**

```sql
SELECT isbn FROM Copies 
GROUP BY isbn HAVING COUNT(*) > 1;
```

### Запрос 9.
**ТОП 10 самых старых книг.**

```sql
SELECT isbn FROM Books 
  ORDER BY year 
    LIMIT 10;
```

### Запрос 10.
**Перечислите все категории в категории “Спорт” (с любым уровнем вложености).**

```sql
WITH RECURSIVE ss(x) AS (
    SELECT CAST('Sport' AS text)
    UNION SELECT name 
      FROM Categories 
        JOIN ss 
          ON ss.c = parent_name
) SELECT * FROM ss WHERE c != 'Sport';
```

# Задача 2

### Запрос 1.
**Добавьте запись о бронировании читателем ‘Василеем Петровым’ книги с ISBN 123456 и номером копии 4.**

```sql
INSERT INTO Bookings(reader_number, isbn, copy_number, return_date) 
SELECT number, '123456', 4, NOW() + '10 day' FROM Readers 
WHERE first_name = 'Василий' 
  AND last_name = 'Петров';
```

### Запрос 2.
**Удалить все книги, год публикации которых превышает 2000 год.**

```sql
DELETE FROM Books 
WHERE year > 2000;
```

### Запрос 3.
**Измените дату возврата для всех книг категории "Базы данных", начиная с 01.01.2016, чтобы они были в заимствовании на 30 дней дольше (предположим, что в SQL можно добавлять числа к датам).**

```sql
UPDATE Bookings
  SET return_date = return_date + 30
    WHERE return_date > '01.01.2016' 
      AND isbn IN (SELECT isbn from book_categories 
                     WHERE category_name = 'Базы данных');
```

# Задача 3
### Запрос 1
```sql
SELECT s.Name, s.MatrNr FROM Student s 
  WHERE NOT EXISTS ( 
    SELECT * FROM Check c WHERE c.MatrNr = s.MatrNr AND c.Note >= 4.0 ); 
```

**Описание:**

Взять всех студентов (имя, id), у которых все оценки <4. То есть, одни незачеты по системе оценивания ВШЭ.

### Запрос 2
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

**Описание:**

Выбрать сумму кредитов курсов для каждого профессора. Если курсов у преподавателя нет - то вывести 0.


### Запрос 3
```sql
SELECT s.Name, p.Note
  FROM Student s, Lecture lec, Check c
  WHERE s.MatrNr = c.MatrNr AND lec.LectNr = c.LectNr AND c.Note >= 4 
    AND c.Note >= ALL ( 
      SELECT c1.Note FROM Check c1 WHERE c1.MatrNr = c.MatrNr ) 
```

**Описание:**

Выбрать студентов и их максимальные оценки, которые >4.
