# Задание 4. Корнилов Георгий, группа БПИ-197

## Задача 1
### а) Какие фамилии читателей в Москве?
```sql
SELECT LastName FROM Reader WHERE Address LIKE '%Москва%';
```

### б) Какие книги (author, title) брал Иван Иванов?
```sql
SELECT Author, Title
FROM Book
         INNER JOIN Borrowing B ON Book.ISBN = B.ISBN
         INNER JOIN Reader R ON R.ID = B.ReaderNr
WHERE LastName = 'Иванов'
  AND FirstName = 'Иван';
```

### в) Какие книги (ISBN) из категории "Горы" не относятся к категории "Путешествия"? Подкатегории не обязательно принимать во внимание!

```sql
SELECT ISBN
FROM BookCat
WHERE CategoryName = 'Горы'
  AND ISBN NOT IN (
    SELECT ISBN
    FROM BookCat
    WHERE CategoryName = 'Путешествия'
);
```

### г) Какие читатели (LastName, FirstName) вернули копию книгу?
(здесь считаем, что читатель вернул книгу, если дата возвращения не NULL и меньше текущей даты)
```sql
SELECT LastName, FirstName
FROM Reader
         INNER JOIN Borrowing B ON Reader.ID = B.ReaderNr
WHERE ReturnDate IS NOT NULL
  AND DATE(ReturnDate) < DATE('now');
```

### д) Какие читатели (LastName, FirstName) брали хотя бы одну книгу (не копию), которую брал также Иван Иванов (не включайте Ивана Иванова в результат)?
```sql
SELECT LastName, FirstName
FROM Reader
         INNER JOIN Borrowing B ON Reader.ID = B.ReaderNr
WHERE B.ISBN IN (
    SELECT ISBN
    FROM Borrowing
             INNER JOIN Reader R ON Borrowing.ReaderNr = R.ID
    WHERE R.FirstName = 'Иван'
      AND R.LastName = 'Иванов'
) AND (LastName != 'Иванов' OR FirstName != 'Иван');
```


## Задача 2
### а) Найдите все прямые рейсы из Москвы в Тверь.
```sql
SELECT TrainNr
FROM Connection
         INNER JOIN Station stationFrom ON stationFrom.ID = FromStation
         INNER JOIN Station stationTo ON stationTo.ID = ToStation
WHERE stationFrom.CityName = 'Москва'
  AND stationTo.CityName = 'Тверь';
```

### б) Найдите все многосегментные маршруты, имеющие точно однодневный трансфер из Москвы в Санкт-Петербург
```sql
SELECT ConnectionFrom.TrainNr
FROM Connection ConnectionFrom
         JOIN Connection ConnectionTo
              ON ConnectionFrom.TrainNr = ConnectionTo.TrainNr AND ConnectionFrom.Arrival < ConnectionTo.Arrival
         JOIN Station stationFirstFrom ON stationFirstFrom.ROWID = ConnectionFrom.FromStation
         JOIN Station stationSecondTo ON stationSecondTo.ROWID = ConnectionTo.ToStation
         JOIN Station stationFirstTo ON stationFirstTo.ROWID = ConnectionFrom.ToStation
         JOIN Station stationSecondFrom ON stationSecondFrom.ROWID = ConnectionTo.FromStation
WHERE DAY(ConnectionFrom.Departure) = DAY(ConnectionTo.Arrival)
  AND stationFirstFrom.CityName = 'Москва'
  AND stationSecondTo.CityName = 'Санкт-Петербург'
  AND stationSecondFrom.CityName != 'Москва'
  AND stationFirstTo.CityName != 'Санкт-Петербург';
```

### в) Что изменится в выражениях для а) и б), если отношение "Connection" не содержит дополнительных кортежей для транзитивного замыкания

Первый запрос станет выглядеть вот так:
```sql
SELECT ConnectionFrom.TrainNr
FROM Connection ConnectionFrom
         INNER JOIN Connection ConnectionTo
              ON ConnectionFrom.TrainNr = ConnectionTo.TrainNr AND ConnectionFrom.Arrival < ConnectionTo.Arrival
         INNER JOIN Station stationFrom ON stationFrom.ROWID = ConnectionFrom.FromStation
         INNER JOIN Station stationTo ON stationTo.ROWID = ConnectionTo.ToStation
WHERE stationFrom.CityName = 'Москва'
  AND stationTo.CityName = 'Тверь';
```

Второй же запрос не изменится, потому что в нем не используется транзитивность.

