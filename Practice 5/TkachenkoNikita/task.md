# Задание 5: Ткаченко Никита, БПИ197
## Задача №1:
* 1) SELECT TITLE, PUBNAME FROM BOOK
* 2) SELECT * FROM BOOK WHERE PAGESNUM = (SELECT MAX(PAGESNUM) FROM BOOK)
* 3) SELECT AUTHOR FROM BOOK GROUP BY AUTHOR HAVING COUNT(AUTHOR) > 5
* 4) SELECT * FROM BOOK WHERE PAGESNUM > (SELECT 2 * SUM(PAGESNUM) / COUNT(PAGESNUM) FROM BOOK)
* 5) SELECT DISTINCT PARENTCAT FROM CATEGORY WHERE NOT (PARENTCAT IS NULL)
* 6) SELECT AUTHOR FROM BOOK GROUP BY AUTHOR ORDER BY COUNT(*) DESC FETCH FIRST 1 ROWS ONLY
* 7) SELECT * FROM READER WHERE READER.ID in (
    SELECT READERNR FROM BORROWING BR INNER JOIN BOOK B on B.ISBN = BR.ISBN
    WHERE AUTHOR = 'Марк Твен'
    GROUP BY READERNR
    HAVING COUNT(READERNR) = (SELECT count(ISBN) FROM BOOK WHERE AUTHOR = 'Марк Твен'))
* 8) SELECT * FROM BOOK B WHERE ISBN IN (SELECT DISTINCT C.ISBN FROM COPY C inner join BOOK B2 on B2.ISBN = C.ISBN WHERE C.COPYNUMBER > 1 )
* 9) SELECT * FROM BOOK ORDER BY PUBYEAR FETCH FIRST 10 ROWS ONLY
* 10) SELECT CATEGORYNAME FROM CATEGORY WHERE PARENTCAT = 'Спорт'

## Задача №2:
* 1) INSERT INTO BORROWING (READERNR, ISBN, COPYNUMBER, RETURNDATE) VALUES 
((SELECT ID FROM READER WHERE FIRSTNAME = 'Василий' AND LASTNAME = 'Петров' FETCH FIRST 1 ROWS ONLY), '123456', 4, null)
* 2) DELETE FROM BOOK WHERE PUBYEAR > 2000
* 3) UPDATE BORROWING SET RETURNDATE = RETURNDATE + 30
WHERE ISBN in (SELECT B.ISBN FROM BOOKCAT
INNER JOIN BORROWING B on BOOKCAT.ISBN = B.ISBN
WHERE CATEGORYNAME = 'Базы данных' AND B.RETURNDATE >= DATE('01.01.2016'))

## Задача №3:
* 1) Возвращает имена студентов и MatrNr студентов для которых данное условие неверно (c.Note >= 4.0)
* 2) Таблица разобьется сначала по ProfNr, затем полученные группы сгрупируются по имени,
далее применится функция которая для каждой группы посчитает сумму кредитов, к ним добавятся
наборы где профессоры, которые не ведут лекции, с нулем кредитов
* 3) Выбираем пары имен студентов и их заметок так, что заметок больше 4 и у студента не было больше 4

*** Apache Derby ***
