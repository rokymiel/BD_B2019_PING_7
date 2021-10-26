# Задание 5
 ## Задание 1
 ### Показать все названия книг вместе с именами издателей.
 ```sql
 SELECT books.title, books.publisher_name FROM books
 ```
 ### В какой книге наибольшее количество страниц?
 ```sql
 SELECT books.isbn FROM books
 where books.page_count = (select max(books.page_count) from books)
 ```
 ### Какие авторы написали более 5 книг?
 ```sql
 SELECT books.author FROM books
 GROUP BY books.author HAVING COUNT(*) > 5
 ```
 ### В каких книгах более чем в два раза больше страниц, чем среднее количество страниц для всех книг?
 ```sql
 SELECT books.isbn FROM books
 WHERE books.page_count > 2 * (SELECT AVG(books.page_count) FROM books)
 ```
 ### Какие категории содержат подкатегории?
 ```sql
 SELECT categories.name FROM categories
 WHERE categories.name IN (
     SELECT DISTINCT categories.parent_name FROM categories
     WHERE not categories.parent_name is NULL
 )
 ```
 ### У какого автора (предположим, что имена авторов уникальны) написано максимальное количество книг?
 ```sql
 SELECT books.author FROM books
 GROUP BY books.author
 ORDER BY COUNT(*) DESC LIMIT 1
 ```
 ### Какие читатели забронировали все книги (не копии), написанные "Марком Твеном"?
 ```sql
 select number from (
     select number, COUNT(*) AS bookNum from (
         select distinct * from bookings
             join readers on bookings.reader_number = readers.number
             join books on bookings.isbn = books.isbn
         where books.author = 'Марк Твен'
         ) as b
     group by number
     ) as readerBookCount
 where bookNum = (SELECT COUNT(*) from books where author = 'Марк Твен')
 ```
 ### Какие книги имеют более одной копии?
 ```sql
 SELECT copies.isbn FROM copies
 GROUP BY copies.isbn HAVING COUNT(*) > 1
 ```
 ### ТОП 10 самых старых книг.
 ```sql
 SELECT books.isbn FROM books
 ORDER BY books.year LIMIT 10 
 ```
 ### Перечислите все категории в категории “Спорт” (с любым уровнем вложености).
 ```sql
 WITH RECURSIVE SportSubcat(x) AS (
     SELECT categories.name FROM categories
     WHERE categories.parent_name = 'Спорт'
     UNION
     SELECT categories.name FROM categories
    JOIN SportSubcat ON categories.parent_name = SportSubcat.x
 ) SELECT x FROM SportSubcat
 ```
 ## Задание 2
 ### Добавьте запись о бронировании читателем ‘Василеем Петровым’ книги с ISBN 123456 и номером копии 4.
 ```sql
INSERT INTO bookings(reader_number, isbn, copy_number, return_date)
SELECT readers.number, '123456', 4, '2021-11-22' FROM readers
WHERE readers.first_name = 'Василий' AND readers.last_name = 'Петров'
 ```
 ### Удалить все книги, год публикации которых превышает 2000 год.
 ```sql
 DELETE FROM books WHERE books.year > 2000
 ```
 ### Измените дату возврата для всех книг категории "Базы данных", начиная с 01.01.2016, чтобы они были в заимствовании на 30 дней дольше (предположим, что в SQL можно добавлять числа к датам).
 ```sql
 UPDATE Borrowing
 SET ReturnDate = ReturnDate + 30
 WHERE ReturnDate > '2016-01-01' AND ISBN IN (
     SELECT ISBN FROM BookCat
     WHERE CategoryName = 'Базы данных'
 )
 ```
 ## Задание 3
 ### 1
 Получить всех студентов, которые получили оценку меньше 4-х или которых не проверили
 ### 2
 Получить номер, имя и сумму весов всех оекций профессора, который ведет лекции, и 0, если он ничего не ведет
 ### 3
 Получить имя и оценку студента, который получил оценку больше 3-х и не меньше его максимальной оценки
