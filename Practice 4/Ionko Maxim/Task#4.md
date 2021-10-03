# Задание №4


## Ионко Максим Олегович БПИ197

## Задача №1
### а) Какие фамилии читателей в Москве?
```sql
SELECT LastName FROM Reader WHERE Address LIKE '%Moscow%';
```
### б) Какие книги (author, title) брал Иван Иванов?
```sql
SELECT Author, Title 

FROM Book

WHERE ISBN = ALL (SELECT ISBN FROM Borrowing

WHERE ReaderNr = ANY (SELECT ID FROM Reader
 
LastName = 'Иванов' AND FirstName = 'Иван')); 
```
### в) Какие книги (ISBN) из категории "Горы" не относятся к категории "Путешествия"?
```sql
SELECT ISBN FROM Book

WHERE ISBN = ALL (SELECT ISBN FROM BookCat

WHERE CategoryName = 'Горы' AND CategoryName != 'Путешествия');
```
### г) Какие читатели (LastName, FirstName) вернули копию книгу?
```sql
SELECT Reader.LastName, Reader.FirstName FROM (
    Join Borrowing ON Borrowing.ReaderNr = Reader.ID
)
WHERE ReturnDate < now; 
```
> исходя из предположения, что все читатели добропорядочные и возвращают книги вовремя

### д) Какие читатели (LastName, FirstName) брали хотя бы одну книгу (не копию), которую брал также Иван Иванов (не включайте Ивана Иванова в результат)
```sql
SELECT Reader.LastName, Reader.FirstName

FROM Borrowing

INNER JOIN Reader ON Borrowing.ReaderNr = Reader.ID

WHERE Borrowing.ISBN = ALL (SELECT ISBN FROM Borrowing

WHERE Reader.FirstName = 'Иван' AND Reader.SecondName = 'Иванов')

AND Reader.FirstName != 'Иван' AND Reader.SecondName != 'Иванов';
```

## Задача №2

### а) Найдите все прямые рейсы из Москвы в Тверь.
```sql
SELECT * FROM Connection

WHERE TrainNr = ALL (SELECT TrainNr FROM Train

WHERE StartStationName = "Москва" AND EndStationName = "Тверь");
```

### б) Найдите все многосегментные маршруты, имеющие точно однодневный трансфер из Москвы в Санкт-Петербург (первое отправление и прибытие в конечную точку должны быть в одну и ту же дату). Вы можете применить функцию DAY () к атрибутам Departure и Arrival, чтобы определить дату.

```sql
SELECT * FROM Connection

WHERE TrainNr = ALL (SELECT TrainNr FROM Train

WHERE StartStationName = "Москва" AND EndStationName = "Тверь"

AND DAY(Departure) = DAY(Arrival));
```