# Задание 4. Поволоцкий В.А

## Задача 1
### А)
```sql
SELECT LastName FROM Reader 
WHERE Address LIKE '%Москва%';
```

### Б) 
```sql
SELECT Author, Title
FROM Book
         INNER JOIN Borrowing  
            ON Book.ISBN = Borrowing.ISBN
         INNER JOIN Reader  
            ON Reader.ID = Borrowing.ReaderNr
WHERE LastName = 'Иванов' AND FirstName = 'Иван';
```

### В)

```sql
SELECT DISTINCT Book.ISBN
FROM Book
         JOIN BookCat
            ON BookCat.ISBN = Book.ISBN
WHERE BookCat.CategoryName != 'Путешествия' AND BookCat.CategoryName = 'Горы';
```

### Г) 
```sql
SELECT LastName, FirstName
FROM Reader
         INNER JOIN Borrowing 
            ON Reader.ID = Borrowing.ReaderNr
WHERE Borrowing.ReturnDate  <= DATE('now');
```

### Д) 
```sql
SELECT DISTINCT Reader.FirstName, Reader.LastName
FROM Borrowing
        JOIN Reader
          ON Reader.ID = Borrowing.ReaderNr
WHERE Borrowing.ISBN IN (SELECT Borrowing.ISBN
                         FROM Borrowing
                            JOIN Reader
                              ON Reader.ID = Borrowing.ReaderNr
    WHERE Reader.FirstName = 'Иван' AND Reader.LastName = 'Иванов')
      AND (Reader.FirstName != 'Иван' OR Reader.LastName != 'Иванов')
```


## Задача 2
### А) 
```sql
SELECT TrainNr
FROM Connection
         INNER JOIN Station stationFrom 
            ON stationFrom.ID = FromStation
         INNER JOIN Station stationTo 
            ON stationTo.ID = ToStation
WHERE stationFrom.CityName = 'Москва' AND stationTo.CityName = 'Тверь';
```
