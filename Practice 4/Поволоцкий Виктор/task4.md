# Задание 4. Корнилов Георгий, группа БПИ-197

## Задача 1
### А)
```sql
SELECT LastName FROM Reader WHERE Address LIKE '%Москва%';
```

### Б) 
```sql
SELECT Author, Title
FROM Book
         INNER JOIN Borrowing B ON Book.ISBN = B.ISBN
         INNER JOIN Reader R ON R.ID = B.ReaderNr
WHERE LastName = 'Иванов'
  AND FirstName = 'Иван';
```

### В)

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

### Г) 
(здесь считаем, что читатель вернул книгу, если дата возвращения не NULL и меньше текущей даты)
```sql
SELECT LastName, FirstName
FROM Reader
         INNER JOIN Borrowing B ON Reader.ID = B.ReaderNr
WHERE ReturnDate IS NOT NULL;
```

### Д) 
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
### А) 
```sql
SELECT TrainNr
FROM Connection
         INNER JOIN Station stationFrom ON stationFrom.ID = FromStation
         INNER JOIN Station stationTo ON stationTo.ID = ToStation
WHERE stationFrom.CityName = 'Москва'
  AND stationTo.CityName = 'Тверь';
```