# Задание 4
## Задание 1
### Пункт a)
```sql
SELECT LastName FROM Reader
WHERE address LIKE '%Москва%'
```
### Пункт б)
```sql
SELECT Author, Title FROM Book, Borrowing, Reader
WHERE
Reader.FirstName = 'Иван' AND Reader.LastName = 'Иванов' AND
Reader.Id = Borrowing.ReaderNr AND
Borrowing.ISBN = Book.ISBN
```
### Пункт в)
```sql
SELECT ISBN FROM BookCat b1
WHERE b1.CategoryName = 'Горы'
AND NOT EXISTS (
SELECT * FROM BookCat b2
WHERE b2.ISBN = b1.ISBN AND b2.CategoryName = 'Путешествия'
)
```
### Пункт г)
```sql
SELECT DISTINCT LastName, FirstName FROM Reader, Borrowing bor
WHERE Reader.ID = bor.ReaderNr AND EXISTS (
    SELECT * FROM Borrowing
    WHERE bor.ISBN = Borrowing.ISBN AND bor.CopyNumber = Borrowing.CopyNumber AND
    bor.ReturnDate > Borrowing.ReturnDate
)
```
### Пункт д)
```sql
SELECT DISTINCT LastName, FirstName FROM Reader, Borrowing
WHERE Reader.ID = Borrowing.ReaderNr AND NOT (FirstName = 'Иван' and LastName = 'Иванов')
AND Borrowing.ISBN in (
    SELECT DISTINCT Borrowing.ISBN FROM Borrowing
    WHERE Borrowing.ReaderNr in (
        SELECT Reader.ID FROM Reader
        WHERE Reader.LastName = 'Иванов' AND Reader.FirstName = 'Иван'
    )
)
```
## Задание 2
### Пункт a)
```sql
SELECT DISTINCT Connection.TrainNr, Connection.Departure, Connection.Arrival FROM Connection
WHERE Connection.FromStation = 'Москва' AND Connection.ToStation = 'Тверь'
```
### Пункт б)
```sql
SELECT TrainNr FROM Connection s
WHERE s.FromStation = 'Москва' AND s.EndStationName = 'Санкт-Петербург' AND DAY(MultiSegment.Departure) = DAY(MultiSegment.Arrival) AND
EXISTS (
    SELECT * FROM Connection c
    WHERE c.EndStationName = s.FromStation OR c.StartStationName = s.EndStationName OR
    (c.FromStation = s.FromStation AND not c.EndStationName = s.EndStationName) OR
    (not c.FromStation = s.FromStation AND c.EndStationName = s.EndStationName)
)
```
### Пункт в)
если отношение "Connection" не содержит дополнительных кортежей для транзитивного замыкания, то можно находить необходимый путь при помощи прохода `FROM Connection с1, Connection с2`, а остальное оставить без изменений

То есть, чтобы найти путь, который может быть не указан явно в отнощшении,  можно:
```sql
SELECT TrainNr FROM Connection c1, Connection c2
WHERE c1.FromStation = 'Москва' AND c2.EndStationName = 'Санкт-Петербург'
```
## Задание 3
Пусть нам даны две таблицы **A** и **B**. 

Для выполнения (Full) Outer Join по некоторым общим столбцам **x<sub>1</sub>, x<sub>2</sub>, ... x<sub>p</sub>**, мы джойним все строки наших таблиц, у которых совпадают все столбцы, а при нехватке значений в других столбацах произвести заполнение значением `null`

| A | B |
|---|---|
| 1 | 3 |
| 2 | 4 |
| 3 | 5 |
| 4 | 6 |

После выполнения (Full) Outer Join

| A    | B    |
|------|------|
| 1    | null |
| 2    | null |
| 3    | 3    |
| 4    | 4    |
| null | 5    |
| null | 6    |

IJ = π<sub>a<sub>1</sub> ... a<sub>m</sub>, b<sub>1</sub> ... b<sub>n</sub> </sub>(σ<sub>∀i A.x<sub>i</sub> = B.x<sub>i</sub></sub>(**A**×**B**))

L = (**A** - π<sub>A</sub>IJ) x {(null, null, ... null)}

R = (π<sub>B</sub>IJ - **B**) x {(null, null, ... null)}

*ИТОГ* (Full) Outer Join = IJ ∪ L ∪ R
