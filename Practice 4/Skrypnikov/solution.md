# Практис четвёртый, про SQL и реляционную алгебру

Диалект SQL == SQLite.

### Задание 1
а) Какие фамилии читателей в Москве?

```sql
SELECT LastName FROM Reader
WHERE Address LIKE '%Moscow%'
```

б) Какие книги (author, title) брал Иван Иванов? 

```sql
SELECT Author, Title FROM
    Book B JOIN Borrowing Borr
        ON B.ISBN = Borr.ISBN
           JOIN Reader R
        ON Borr.ReaderNr = R.ID
WHERE R.LastName = 'Иванов' AND R.FirstName = 'Иван'
```

в) Какие книги (ISBN) из категории "Горы" не относятся к категории "Путешествия"? Подкатегории не обязательно принимать во внимание!

```sql
SELECT Bc.ISBN FROM
	BookCat Bc 
WHERE Bc.CategoryName = 'Путешествия'
EXCEPT
SELECT Bc.ISBN FROM
	BookCat Bc 
WHERE Bc.CategoryName = 'Горы'
```

г) Какие читатели (LastName, FirstName) вернули копию книгу?

```sql
SELECT R.FirstName, R.LastName FROM
	Reader R JOIN Borrowing Br
    	ON Br.ReaderNr = R.ID
WHERE DATE(Br.ReturnDate) < DATE('now')
```

д) Какие читатели (LastName, FirstName) брали хотя бы одну книгу (не копию), которую брал также Иван Иванов (не включайте Ивана Иванова в результат)?

```sql
SELECT DISTINCT R.FirstName, R.LastName FROM
	Borrowing Br JOIN Reader R
    	ON Br.ReaderNr = R.ID
WHERE Br.ISBN IN (SELECT Br.ISBN FROM
                      Borrowing Br JOIN Reader R
                          ON Br.ReaderNr = R.ID
                  WHERE R.FirstName = 'Иван' AND R.LastName = 'Иванов')
AND (R.FirstName != 'Иван' OR R.LastName != 'Иванов')
```


### Задание 2

а) Найдите все прямые рейсы из Москвы в Тверь. 

```sql
SELECT Tr.TrainNr, C.Departure, C.Arrival FROM
	Connection C JOIN Train Tr 
    		ON Tr.TrainNr = C.TrainNr
WHERE C.FromStation = 'Москва' AND C.ToStation = 'Тверь'
```

б) Найдите все многосегментные маршруты, имеющие точно однодневный трансфер из Москвы в Санкт-Петербург (первое отправление и прибытие в конечную точку должны быть в одну и ту же дату)

```sql
SELECT DISTINCT Tr.TrainNr FROM
(SELECT C.TrainNr FROM
     Connection C GROUP BY C.TrainNr
     HAVING COUNT(*) > 1) Tr JOIN Connection C
ON C.TrainNr = Tr.TrainNr
WHERE C.Departure = 'Москва' AND С.Arrival = 'Санкт-Петербург'
AND JULIANDAY(C.Departure, '-1 day') = JULIANDAY(C.Arrival)
```

в) Что изменится в выражениях для а) и б), если отношение "Connection" не содержит дополнительных кортежей для транзитивного замыкания, поэтому многосегментный маршрут Москва-> Тверь-> Санкт-Петербург содержит только кортежи Москва-> Тверь и Тверь-Санкт-Петербург?

Нужно будет обходить таблицу либо специальным синтаксисом с лекции, если мы в Oracle, либо через вспомогательную таблицу с WITH, которая бы и делала транзитивное замыкание.


### Задание 3
OuterJoin в реляционной алгебре

![](TexOuter.png)
