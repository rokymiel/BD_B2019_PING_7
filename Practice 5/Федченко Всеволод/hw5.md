## Задание 4
### Задача 1
#### а) Показать все названия книг вместе с именами издателей.
```sql
SELECT DISTINCT Title, PubName 
FROM Book 
```
#### б) В какой книге наибольшее количество страниц?
```sql
SELECT ISBN
FROM Book
ORDER BY PagesNum DESC
LIMIT 1
```

#### в) Какие авторы написали более 5 книг?
```sql
SELECT Author
FROM Book
GROUP BY Author
HAVING COUNT(*) > 5
```

#### г) В каких книгах более чем в два раза больше страниц, чем среднее количество страниц для всех книг?
```sql
SELECT ISBN
FROM Book
WHERE PagesNum > 2 * (
    SELECT AVG(PagesNum)
    FROM Book
)
```

#### д) Какие категории содержат подкатегории?
```sql
SELECT DISTINCT p.CategoryName
FROM Category p
INNER JOIN Category c
	ON p.CategoryName = c.ParentCat
```
#### е) У какого автора (предположим, что имена авторов уникальны) написано максимальное количество книг?
```sql
SELECT Author
FROM (
    SELECT Author, COUNT(*) AS BookNum
    FROM Book
    GROUP BY Author)
ORDER BY BookNum DESC
LIMIT 1
```

#### ж) Какие читатели забронировали все книги (не копии), написанные "Марком Твеном"?
```sql
WITH DistBrws AS (
	SELECT DISTINCT r.ID, br.ISBN
	FROM Borrowing br 
	JOIN Reader r 
	  ON r.ID = br.ReaderNr
	JOIN Book b
	  ON br.ISBN = b.ISBN
	WHERE b.Author = 'Марк Твен'
)

SELECT ID
FROM (
	SELECT ID, COUNT(*) AS UniqueBooksNum
	FROM DistBrws 
	GROUP BY ID
)
WHERE UniqueBooksNum = (
   SELECT COUNT(*) 
   FROM Book 
   WHERE Author = 'Марк Твен'
)
```

#### з) Какие книги имеют более одной копии?
```sql
SELECT ISBN
FROM Copy  
GROUP BY ISBN 
HAVING COUNT(*) > 1
```

#### и) ТОП 10 самых старых книг
```sql
SELECT ISBN 
FROM Book 
ORDER BY PubYear
LIMIT 10
```


#### к) Перечислите все категории в категории “Спорт” (с любым уровнем вложености).
```sql
WITH RECURSIVE recurCat AS (
SELECT CategoryName 
FROM Category
WHERE ParentCat = 'Спорт'

UNION

SELECT c.CategoryName 
FROM Category c 
JOIN recurCat p
  ON c.ParentCat = p.CategoryName 
) 
SELECT * FROM recurCat 
```

### Задача 2
#### a) Добавьте запись о бронировании читателем ‘Василеем Петровым’ книги с ISBN 123456 и номером копии 4.
```sql
INSERT INTO Borrowing(ReaderNr, ISBN, CopyNumber)
SELECT r.ID, '123456', 4 
FROM Reader r
WHERE r.FirstName = 'Василий' 
  AND r.LastName = 'Петров' 
LIMIT 1
```

#### б) Удалить все книги, год публикации которых превышает 2000 год.
```sql
DELETE FROM Book 
WHERE PubYear > 2000
```

#### в) Измените дату возврата для всех книг категории "Базы данных", начиная с 01.01.2016, чтобы они были в заимствовании на 30 дней дольше (предположим, что в SQL можно добавлять числа к датам).
```sql
UPDATE Borrowing
SET ReturnDate = ReturnDate + interval '30 day'
WHERE ReturnDate > DATE('01.01.2016')
  AND Borrowing.ISBN IN (
    SELECT DISTINCT ISBN 
    FROM BookCat
    WHERE CategoryName = 'Базы данных'
  )
```

### Задача 3
1.
```sql
SELECT s.Name, s.MatrNr FROM Student s 
  WHERE NOT EXISTS ( 
    SELECT * FROM Check c WHERE c.MatrNr = s.MatrNr AND c.Note >= 4.0 ) ; 
```
Будут выбраны все студенты (точнее, их имя и id) у которых Note меньше 4.

2.
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
Для каждого профессора будет выведено его имя, id, а так же сумма кредитов по всем лекциям, которые ведет профессор. Для тех профессоров, которые не ведут лекции в качестве суммы кредитов будет указан 0.

3.
```sql
SELECT s.Name, p.Note
  FROM Student s, Lecture lec, Check c
  WHERE s.MatrNr = c.MatrNr AND lec.LectNr = c.LectNr AND c.Note >= 4 
    AND c.Note >= ALL ( 
      SELECT c1.Note FROM Check c1 WHERE c1.MatrNr = c.MatrNr ) 

```
Будут выбраны все имена студентов и их лучшие оценки, если они (оценки) не меньше 4
