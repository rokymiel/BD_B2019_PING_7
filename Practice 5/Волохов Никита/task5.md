# Курс "Базы данных"

## Домашняя работа 5. Волохов Никита Алексеевич, БПИ197

### **_Задача 1._**
**а) Показать все названия книг вместе с именами издателей.**
```sql
SELECT Title, PubName FROM Book
```
**б) В какой книге наибольшее количество страниц?**
```sql
SELECT ISBN FROM Book 
ORDER BY PagesNum DESC 
LIMIT 1
```
Дополнительное решение: Такой вариант мне не нравится, потому что выведется 2 столбца вместо просто ISBN
```sql
SELECT ISBN, MAX(PagesNum) AS NumOfPages FROM Book
```
**в) Какие авторы написали более 5 книг?**
```sql
SELECT Author from Book 
GROUP BY AUTHOR HAVING COUNT(*) > 5
```
**г) В каких книгах более чем в два раза больше страниц, чем среднее количество страниц для всех книг?**
```sql
SELECT ISBN FROM Book
WHERE PagesNum > (
    SELECT 2 * AVG(PagesNum) FROM Book
)
```
**д) Какие категории содержат подкатегории?**
```sql
SELECT CategoryName FROM Category as c1
INNER JOIN Category AS c2 ON c1.CategoryName = c2.ParentCat
```
**е) У какого автора (предположим, что имена авторов уникальны) написано максимальное количество книг?**
```sql
SELECT Author from (SELECT Author, COUNT(*) AS NumOfBooks FROM Book GROUP BY Author)
ORDER BY NumOfBooks DESC LIMIT 1
```
**ж) Какие читатели забронировали все книги (не копии), написанные "Марком Твеном"?**
```sql
WITH BorrowsOfDistinctISBN AS (
    SELECT DISTINCT Reader.ID, Borrowing.ISBN
    FROM Borrowing JOIN Reader ON Reader.ID = Borrowing.ReaderNr
                   JOIN Book ON Borrowing.ISBN = Book.ISBN
    WHERE Book.Author = 'Марк Твен'
)

SELECT ID
FROM (
    SELECT ID, COUNT(*) AS NumOfUniqueBooks
    FROM BorrowsOfDistinctISBN 
    GROUP BY ID
)
WHERE NumOfUniqueBooks = (
    SELECT COUNT(*) 
    FROM Book 
    WHERE Author = 'Марк Твен'
)
```
**з) Какие книги имеют более одной копии?**
```sql
SELECT ISBN FROM Copy GROUP BY ISBN HAVING COUNT(*) > 1 
```
**и) ТОП 10 самых старых книг.**
```sql
SELECT ISBN FROM Book ORDER BY PubYear LIMIT 10
```
**к) Перечислите все категории в категории “Спорт” (с любым уровнем вложености).**
```sql
WITH RECURSIVE Subcats AS (
    SELECT Category.CategoryName FROM Category WHERE Category.ParentCat = "Спорт"
    UNION ALL
    SELECT Category.CategoryName FROM Category JOIN Subcats ON Category.ParentCat = Subcat.CategoryName
)

SELECT * FROM Subcats
```

### **_Задача 2._** 
**a) Добавьте запись о бронировании читателем ‘Василеем Петровым’ книги с ISBN 123456 и номером копии 4.**
```sql
INSERT INTO Borrowing(ReaderNr, ISBN, CopyNumber)
SELECT ID, "123456", 4 FROM Reader
WHERE FirstName = "Василий" AND LastName = "ПЕТРОВ" LIMIT 1
```
**б) Удалить все книги, год публикации которых превышает 2000 год.**
```sql
DELETE FROM Book WHERE PubYear > 2000
```
**в) Измените дату возврата для всех книг категории "Базы данных", начиная с 01.01.2016, чтобы они были в заимствовании на 30 дней дольше (предположим, что в SQL можно добавлять числа к датам).**
```sql
UPDATE Borrowing
SET ReturnDate = ReturnDate + 30
WHERE ReturnDate > DATE('2016.01.01') AND Borrowing.ISBN IN (
    SELECT DISTINCT ISBN 
    FROM BookCat
    WHERE CategoryName = 'Базы данных'
)
```

### **_Задача 3._**
**1)** Выбрать таких студентов (имя: Name и MatrNr: индентификатор), у которых оценка: Note < 4.0.

**2)** Вывести для каждого профессора сумму кредитов лекций, проводимых им. Если профессор не ведет ни одной лекции, то сумма кредитов = 0.

**3)** Для каждого студента (имя: Name) выводит оценку: Note >= 4.0. Такая оценка является максимальной для студента.
