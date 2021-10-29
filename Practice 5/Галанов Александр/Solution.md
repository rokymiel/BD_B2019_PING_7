# Курс "Базы данных"

## Задание 5
 
### задача 1
 
1. SELECT TITLE, PUBNAME
FROM BOOK;

2. SELECT *
FROM BOOK
WHERE PAGESNUM = (SELECT MAX(PAGESNUM) FROM BOOK)
LIMIT 1;

3. SELECT AUTHOR
FROM BOOK
GROUP BY AUTHOR
HAVING count(*) > 5;

4. SELECT *
FROM BOOK
WHERE PAGESNUM > (SELECT 2 * AVG(PAGESNUM) FROM BOOK);

5. SELECT DISTINCT PARENTCAT
FROM CATEGORY
WHERE PARENTCAT IS NOT NULL;

6. SELECT AUTHOR
FROM BOOK
GROUP BY AUTHOR
HAVING count(*) =
       (SELECT MAX(AC.BOOK_COUNT) FROM (SELECT count(*) as BOOK_COUNT FROM BOOK GROUP BY AUTHOR) as AC)
LIMIT 1;

7. SELECT *
FROM READER
WHERE READER.ID in (
    SELECT READERNR
    FROM BORROWING
             INNER JOIN BOOK ON BORROWING.ISBN = BOOK.ISBN
    WHERE AUTHOR = 'Марк Твен'
    GROUP BY READERNR
    HAVING count(*) = (SELECT count(*) FROM BOOK WHERE AUTHOR = 'Марк Твен'));

8. SELECT *
FROM BOOK
WHERE ISBN IN
      (SELECT ISBN FROM COPY GROUP BY ISBN HAVING count(*) > 1);

9. SELECT *
FROM BOOK
ORDER BY PUBYEAR
LIMIT 10;

10. SELECT CATEGORYNAME
FROM CATEGORY
WHERE PARENTCAT = 'Спорт'; 

### задача 2

1. INSERT INTO BORROWING VALUES ((SELECT ID FROM READER WHERE FIRSTNAME='Василий' AND LASTNAME='Петров'),'123456',4,DATE(120));

2. DELETE FROM BOOK WHERE PUBYEAR > 2000; (На foreign ключи повешана стратегия ON DELETE CASCADE)

3. UPDATE BORROWING SET RETURNDATE = RETURNDATE + 30
WHERE ISBN IN (SELECT ISBN FROM BOOKCAT WHERE CATEGORYNAME = 'Базы данных')
AND RETURNDATE >= DATE('01.01.2016');

### задача 3

1. Вернет имена и id всех студентов, у которых Note < 4.

2. Возвращает id профессоров, их имена, и сумму кредитов по всем лекциям, где они учавствуют.
 Также в запросе будут отражены id и имена профессоров, которые
не ведут ни одной лекции. Количество их кредитов будет равняться 0.

3. Возвращает имена и количество заметок студентов. При этом для каждого студента
количество заметок максимально и больше или равно 4.