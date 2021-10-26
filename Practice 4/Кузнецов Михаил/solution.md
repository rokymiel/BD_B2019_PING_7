# Задание 4
## Задание 1
### Пункт a)
```sql
SELECT DISTINCT LastName FROM Reader
WHERE  Address LIKE '%Москва%'
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
SELECT DISTINCT ISBN FROM BookCat
WHERE BookCat.CategoryName = 'Горы'
EXCEPT
SELECT ISBN FROM BookCat
WHERE BookCat.CategoryName = 'Путешествия'
```
### Пункт г)
```sql
SELECT DISTINCT Reader.LastName, Reader.FirstName FROM Reader, Borrowing
WHERE Reader.ID = Borrowing.ReaderNr AND Borrowing.ReturnDate < DATE()

```
### Пункт д)
```sql
SELECT DISTINCT LastName, FirstName FROM Reader, Borrowing
WHERE Reader.ID = Borrowing.ReaderNr AND NOT (FirstName = 'Иван' AND LastName = 'Иванов')
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
JOIN Station startST ON startST.Name = Connection.FromStation
JOIN Station endST ON endST.Name = Connection.ToStation
WHERE startST.CityName = 'Москва' AND endST.CityName = 'Тверь'
```
### Пункт б)
```sql
SELECT DISTINCT Connection.TrainNr, Connection.Departure, Connection.Arrival FROM Connection
JOIN Station startST ON startST.Name = Connection.FromStation
JOIN Station endST ON endST.Name = Connection.ToStation
WHERE startST.CityName = 'Москва' AND endST.CityName = 'Санкт-Петербург' 
AND DAY(Connection.Departure) = DAY(Connection.Arrival)
AND Connection.TrainNr in (
    SELECT Connection.TrainNr FROM Connection
    GROUP BY Connection.TrainNr HAVING COUNT(Connection.TrainNr) > 1
)
```
### Пункт в)
Изменениия для а)
```sql
SELECT DISTINCT conFrom.TrainNr, conFrom.Departure, conTo.Arrival FROM Connection conFrom
JOIN Connection conTo ON conFrom.TrainNr = conTo.TrainNr
JOIN Station startST ON startST.Name = conFrom.FromStation
JOIN Station endST ON endST.Name = conTo.ToStation
WHERE startST.CityName = 'Москва' AND endST.CityName = 'Тверь'
```
Изменениия для б)
```sql
SELECT DISTINCT conFrom.TrainNr, conFrom.Departure, conTo.Arrival FROM Connection conFrom
JOIN Connection conTo ON conFrom.TrainNr = conTo.TrainNr
JOIN Station startST ON startST.Name = conFrom.FromStation
JOIN Station endST ON endST.Name = conTo.ToStation
WHERE startST.CityName = 'Москва' AND endST.CityName = 'Санкт-Петербург' 
AND DAY(conFrom.Departure) = DAY(conTo.Arrival)
AND conFrom.TrainNr in (
    SELECT Connection.TrainNr FROM Connection
    GROUP BY Connection.TrainNr HAVING COUNT(Connection.TrainNr) > 1
)
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
