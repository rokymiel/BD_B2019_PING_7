## Задача 1

а)
```sql
SELECT DISTINCT LastName FROM Reader WHERE Address LIKE '%Москва%';
```

б)
```sql
SELECT DISTINCT Author, Title FROM Book
JOIN Borrowing ON Borrowing.ISBN = Book.ISBN
JOIN Reader ON Borrowing.ReaderNr = Reader.ID
WHERE Reader.FirstName = 'Иван' AND Reader.LastName = 'Иванов';
```

в)
```sql
SELECT DISTINCT ISBN FROM BookCat
WHERE CategoryName = 'Горы' EXCEPT
SELECT DISTINCT ISBN FROM BookCat
WHERE CategoryName = 'Путешествия';
```

г)
```sql
SELECT DISTINCT Reader.LastName, Reader.FirstName FROM Reader
JOIN Borrowing ON Borrowing.ReaderNr = Reader.ID
WHERE Borrowing.ReturnDate < date('now');
```

д)
```sql
SELECT DISTINCT Reader.LastName, Reader.FirstName FROM Borrowing
JOIN Reader ON Borrowing.ReaderNr = Reader.ID
WHERE Borrowing.ISBN IN (
    SELECT Borrowing.ISBN FROM Borrowing
        JOIN Reader ON Borrowing.ReaderNr = Reader.ID
    WHERE Reader.FirstName = 'Иван' AND Reader.LastName = 'Иванов')
  AND (Reader.FirstName != 'Иван' OR Reader.LastName != 'Иванов');
```

## Задача 2

а)
```sql
SELECT DISTINCT TrainNr FROM Connection
JOIN Station s1 ON s1.Name = Connection.FromStation
JOIN Station s2 ON s2.Name = Connection.ToStation
WHERE s1.CityName = 'Москва' AND s2.CityName = 'Тверь';
```

б)
```sql
SELECT DISTINCT Connection.TrainNr FROM Connection
WHERE Connection.FromStation = 'Москва'
  AND Connection.ToStation = 'Санкт-Петербург'
  AND day(Connection.Arrival) = day(Connection.Departure)
  AND Connection.TrainNr IN (
    SELECT Connection.TrainNr FROM Connection
    GROUP BY Connection.TrainNr
    HAVING count(Connection.TrainNr) > 1);
```

в)
```sql
SELECT DISTINCT departures.TrainNr
FROM (
         SELECT Connection.Departure, Connection.TrainNr FROM Connection
         WHERE Connection.FromStation = 'Москва'
     ) departures INNER JOIN (
        SELECT Connection.Arrival, Connection.TrainNr FROM Connection
        WHERE Connection.ToStation = 'Санкт-Петербург'
    ) arrivals ON departures.TrainNr = arrivals.TrainNr
WHERE julianday(dep.Departure) = julianday(arr.Arrival)
  AND dep.TrainNr IN (SELECT Connection.TrainNr FROM Connection
    GROUP BY Connection.TrainNr HAVING count(*) > 1);
```
