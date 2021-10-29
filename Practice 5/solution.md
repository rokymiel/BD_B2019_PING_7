# Задача 1
Запросы 2 и 6 посчитаны как для уникальных объектов (вроде, так можно было)б
### Запрос 1.
Показать все названия книг вместе с именами издателей.

```sql
SELECT B.Title, B.PubName FROM Book B
```

### Запрос 2.
В какой книге наибольшее количество страниц?

```sql
SELECT B.ISBN FROM
Book B ORDER BY B.PagesNum DESC LIMIT 1
```

### Запрос 3.
Какие авторы написали более 5 книг?

```sql
SELECT B.Author FROM
Book B GROUP BY B.Author HAVING COUNT(*) > 5;
```

### Запрос 4.
В каких книгах более чем в два раза больше страниц, чем среднее количество страниц для всех книг?

```sql
SELECT B.ISBN FROM
Book B WHERE B.PagesNum > (SELECT 2 * AVG(B.PagesNum) FROM Book B)
```

### Запрос 5.
Какие категории содержат подкатегории?

```sql
SELECT C.CategoryName FROM
Category C WHERE C.CategoryName IN (SELECT C.ParentCat FROM Category C WHERE C.ParentCat IS NOT NULL)
```

### Запрос 6.
У какого автора (предположим, что имена авторов уникальны) написано максимальное количество книг?

```sql
SELECT B.Author FROM (
	SELECT B.Author, COUNT(B.ISBN) AS CNT FROM Book B GROUP BY B.Author
) B ORDER BY CNT DESC LIMIT 1
```

### Запрос 7.
Какие читатели забронировали все книги (не копии), написанные "Марком Твеном"?

```sql
SELECT Rdr.ID FROM
Reader Rdr JOIN Borrowing Br ON Rdr.ID = Br.ReaderNr JOIN Book B ON Br.ISBN = B.ISBN
WHERE B.Author = 'Марк Твен'
GROUP BY Rdr.ID HAVING COUNT(DISTINCT B.ISBN) = (
	SELECT COUNT(*) FROM Book B WHERE B.Author = 'Марк Твен'
)
```

### Запрос 8.
Какие книги имеют более одной копии?

```sql
SELECT C.ISBN FROM Copy C GROUP BY C.ISBN HAVING COUNT(*) > 1
```

### Запрос 9.
ТОП 10 самых старых книг.

```sql
SELECT B.ISBN FROM Book B ORDER BY B.PubYear LIMIT 10
```

### Запрос 10.
Перечислите все категории в категории “Спорт” (с любым уровнем вложености).

```sql
WITH RECURSIVE SportSubcat(x) AS (
    SELECT C.CategoryName FROM Category C WHERE C.ParentCat = 'Спорт'
    UNION
    SELECT C.CategoryName FROM Category C JOIN SportSubcat S ON C.ParentCat = S.x
) SELECT x FROM SportSubcat
```

# Задача 2

### Запрос 1.
Добавьте запись о бронировании читателем ‘Василеем Петровым’ книги с ISBN 123456 и номером копии 4.

```sql
INSERT INTO Borrowing(ReaderNr, ISBN, CopyNumber)
SELECT R.ID, '123456', 4 FROM Reader R 
WHERE R.FirstName = 'Василий' AND R.LastName = 'Петров' LIMIT 1
```

### Запрос 2.
Удалить все книги, год публикации которых превышает 2000 год.

```sql
DELETE FROM Book WHERE PubYear > '2000'
```

### Запрос 3.
Измените дату возврата для всех книг категории "Базы данных", начиная с 01.01.2016, чтобы они были в заимствовании на 30 дней дольше (предположим, что в SQL можно добавлять числа к датам).

```sql
UPDATE Borrowing
SET ReturnDate = ReturnDate + 30
WHERE ReturnDate > '01.01.2016' AND ISBN IN (SELECT ISBN FROM Book WHERE Category='Базы данных')
```

# Задача 3
### Запрос 1
```sql
SELECT s.Name, s.MatrNr FROM Student s 
  WHERE NOT EXISTS ( 
    SELECT * FROM Check c WHERE c.MatrNr = s.MatrNr AND c.Note >= 4.0 ) ; 
```

Значение: выбрать всех таких студентов (имя, айди) таких, что у них все Note меньше 4 (ОНИ НЕ СДАЛИ :((((( ).

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

Значение: вывести для каждого профессора кредитную нагрузку (сколько кредитов весят все курсы в сумме для каждого из профессора), а для тех, у кого нет курсов -- вывести 0.

### Запрос 3
```sql
SELECT s.Name, p.Note
  FROM Student s, Lecture lec, Check c
  WHERE s.MatrNr = c.MatrNr AND lec.LectNr = c.LectNr AND c.Note >= 4 
    AND c.Note >= ALL ( 
      SELECT c1.Note FROM Check c1 WHERE c1.MatrNr = c.MatrNr ) 
```

Значение: выбрать студентов с их максимальными оценками из тех, что больше 4.
