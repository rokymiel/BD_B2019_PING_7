## Задание 5

В задании используется диалект PostgreSQL.

### Задача 1

1) Показать все названия книг вместе с именами издателей.
```sql
SELECT TITLE, PUBNAME
FROM BOOK
```

2) В какой книге наибольшее количество страниц?
```sql
SELECT *
FROM BOOK
ORDER BY PAGESNUM DESC
LIMIT 1
```

3) Какие авторы написали более 5 книг?
```sql
SELECT AUTHOR
FROM BOOK
GROUP BY AUTHOR
HAVING COUNT(*) > 5
```

4) В каких книгах более чем в два раза больше страниц, чем среднее количество страниц для всех книг? 
```sql
SELECT *
FROM BOOK
WHERE PAGESNUM >
	(SELECT AVG(PAGESNUM) * 2
		FROM BOOK)
```

5) Какие категории содержат подкатегории?
```sql
SELECT PARENTCAT
FROM CATEGORY
WHERE PARENTCAT IS NOT NULL
  AND CATEGORYNAME IS NOT NULL
```

6) У какого автора (предположим, что имена авторов уникальны) написано максимальное количество книг?
```sql
SELECT AUTHOR
FROM BOOK
GROUP BY AUTHOR
ORDER BY COUNT(*) DESC
LIMIT 1
```

7) Какие читатели забронировали все книги (не копии), написанные "Марком Твеном"?
```sql
SELECT *
FROM READER
         JOIN BORROWING ON READER.ID = BORROWING.READERNR
         JOIN BOOK ON BORROWING.ISBN = BOOK.ISBN
WHERE AUTHOR = 'Марк Твен'
GROUP BY ID
HAVING COUNT(DISTINCT BOOK.ISBN) =
       (SELECT COUNT(*)
        FROM BOOK
        WHERE AUTHOR = 'Марк Твен')
```

8) Какие книги имеют более одной копии?
```sql
SELECT *
FROM BOOK
WHERE ISBN in
		(SELECT ISBN
			FROM COPY
			GROUP BY ISBN
			HAVING COUNT(*) > 1)
```

9) ТОП 10 самых старых книг
```sql
SELECT *
FROM BOOK
ORDER BY PUBYEAR
LIMIT 10
```

10) Перечислите все категории в категории “Спорт” (с любым уровнем вложености).
```sql
WITH RECURSIVE SPORTCAT(CATNAME) AS
                   (SELECT CATEGORYNAME
                    FROM CATEGORY
                    WHERE PARENTCAT = 'Спорт'
                    UNION
                    SELECT CATEGORYNAME
                    FROM CATEGORY
                             INNER JOIN SPORTCAT ON CATEGORY.PARENTCAT = SPORTCAT.CATNAME)
SELECT *
FROM SPORTCAT
```

### Задача 2

1) Добавьте запись о бронировании читателем ‘Василием Петровым’ книги с ISBN 123456 и номером копии 4.
```sql
INSERT INTO BORROWING (READERNR, ISBN, COPYNUMBER)
				(SELECT ID, '123456', 4
					FROM READER
					WHERE FIRSTNAME = 'Василий'
					  AND LASTNAME = 'Петров')
```

2) Удалить все книги, год публикации которых превышает 2000 год.
```sql
DELETE
FROM BOOK
WHERE TO_DATE(PUBYEAR, 'YYYY-MM-DD') >= TO_DATE('2001-01-01', 'YYYY-MM-DD')
```

3) Измените дату возврата для всех книг категории "Базы данных", начиная с 01.01.2016, чтобы они были в заимствовании на 30 дней дольше (предположим, что в SQL можно добавлять числа к датам).
```sql
UPDATE BORROWING
SET RETURNDATE = RETURNDATE + 30
WHERE RETURNDATE > '2016-01-01'
	AND ISBN in
			(SELECT ISBN
				FROM BOOKCAT
				WHERE CATEGORYNAME = 'Базы данных')
```

### Задача 3
1) 
```sql
SELECT s.Name, s.MatrNr FROM Student s 
  WHERE NOT EXISTS ( 
    SELECT * FROM Check c WHERE c.MatrNr = s.MatrNr AND c.Note >= 4.0 ) ;
```
Найти имена и номера всех студентов, которых нет среди тех, кого проверили и кто получил отметку (оценку?) выше 3 (то есть найти тех кого не проверили или кто не сдал на 4+).

2)
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
Вывести номера, имена и сумму весов всех лекций для профессоров, которые ведут лекции, и 0, если они ничего не ведут.

3)
```sql
SELECT s.Name, p.Note
  FROM Student s, Lecture lec, Check c
  WHERE s.MatrNr = c.MatrNr AND lec.LectNr = c.LectNr AND c.Note >= 4 
    AND c.Note >= ALL ( 
      SELECT c1.Note FROM Check c1 WHERE c1.MatrNr = c.MatrNr )
```
Вывести имена и оценки тех студентов, чьи оценки больше 3 и больше или равны (то есть равны) его максимальной оценке.