# Задание №4. Кочарян Тигран БПИ197
## Задача 1
### Запрос 1
Какие фамилии читателей в Москве?

```sql
SELECT LastName FROM Reader WHERE Address LIKE '%Москва%'
```

### Запрос 2
Какие книги (author, title) брал Иван Иванов?

```sql
SELECT DISTINCT Author, Title FROM Book
JOIN Borrowing 
  ON Borrowing.ISBN = Book.ISBN
JOIN Reader 
  ON Borrowing.ReaderNr = Reader.ID
WHERE Reader.FirstName = 'Иван' AND Reader.LastName = 'Иванов'
```

### Запрос 3
Какие книги (ISBN) из категории "Горы" не относятся к категории "Путешествия"?
Подкатегории не обязательно принимать во внимание!

```sql
SELECT DISTINCT ISBN FROM 
	BookCat WHERE CategoryName = 'Горы' 
EXCEPT SELECT DISTINCT ISBN FROM 
	BookCat WHERE CategoryName = 'Путешествия'
```

### Запрос 4
Какие читатели (LastName, FirstName) брали книги, которые были возвращены?

```sql
SELECT DISTINCT Reader.LastName, Reader.FirstName FROM Reader
  JOIN Borrowing 
  ON Borrowing.ReaderNr = Reader.ID
WHERE DATE(Borrowing.ReturnDate) < DATE('now')
```

### Запрос 5
Какие читатели (LastName, FirstName) брали хотя бы одну книгу,
которую брал также Иван Иванов (не включайте Ивана Иванова в результат)?

```sql
SELECT DISTINCT Reader.LastName, Reader.FirstName FROM Borrowing
JOIN Reader 
  ON Borrowing.ReaderNr = Reader.ID
WHERE Borrowing.ISBN IN (
  SELECT Borrowing.ISBN FROM Borrowing
  JOIN Reader 
    ON Borrowing.ReaderNr = Reader.ID
  WHERE Reader.FirstName = 'Иван' AND Reader.LastName = 'Иванов'
) AND (Reader.FirstName != 'Иван' OR Reader.LastName != 'Иванов')
```

## Задача 2
### Запрос 1
Найдите все прямые рейсы из Москвы в Тверь.

```sql
SELECT DISTINCT TrainNr FROM Connection
JOIN Station station1 
  ON station1.Name = Connection.FromStation
JOIN Station station2 
  ON station2.Name = Connection.ToStation
WHERE station1.CityName = 'Москва' AND station2.CityName = 'Тверь'
EXCEPT SELECT DISTINCT TrainNr FROM Connection
JOIN Station station1 
  ON station1.Name = Connection.FromStation
JOIN Station station2 
  ON station2.Name = Connection.ToStation
WHERE station1.CityName != 'Москва' OR station2.CityName != 'Тверь'
```

### Запрос 2
Найдите все многосегментные маршруты, имеющие точно однодневный трансфер из Москвы в Санкт-Петербург
(первое отправление и прибытие в конечную точку должны быть в одну и ту же дату).
Вы можете применить функцию DAY() к атрибутам Departure и Arrival, чтобы определить дату.

```sql
SELECT DISTINCT TrainNr FROM Connection
JOIN Station station1 
  ON station1.Name = Connection.FromStation
JOIN Station station2 
  ON station2.Name = Connection.ToStation
WHERE station1.CityName = 'Москва' AND station2.CityName = 'Санкт-Петербург' AND DAY(Arrival) = day(Departure)
INTERSECT SELECT DISTINCT TrainNr FROM Connection
JOIN Station station1 
  ON station1.Name = Connection.FromStation
JOIN Station station2 
  ON station2.Name = Connection.ToStation
WHERE station1.CityName != 'Москва' OR station2.CityName != 'Санкт-Петербург'
```

### Запрос 3
Что изменится в выражениях для а) и б), если отношение "Connection" не содержит дополнительных кортежей для транзитивного замыкания,
поэтому многосегментный маршрут Москва -> Тверь -> Санкт-Петербург содержит только кортежи Москва -> Тверь и Тверь -> Санкт-Петербург?

Первой запрос станет выглядеть так:

```sql
SELECT DISTINCT TrainNr FROM Connection
JOIN Station station1 
  ON station1.Name = Connection.FromStation
JOIN Station station2 
  ON station2.Name = Connection.ToStation
WHERE station1.CityName = 'Москва' AND station2.CityName = 'Тверь'
```

Второй запрос станет выглядеть так:

```sql
SELECT DISTINCT connection1.TrainNr FROM Connection connection1
JOIN Connection connection2 
  ON connection2.FromStation = connection1.ToStation AND connection2.TrainNr = connection1.TrainNr
JOIN Station station1 
  ON station1.Name = connection1.FromStation
JOIN Station station2 
  ON station2.Name = connection2.ToStation
WHERE station1.CityName = 'Москва' AND station2.CityName = 'Санкт-Петербург' AND DAY(connection2.Arrival) DAYday(connection1.Departure)
```

## Задача 3
Представьте внешнее объединение (outer join) в виде выражения реляционной алгебры с использованием только базовых операций
(select, project, cartesian, rename, union, minus)

Пусть у нас имеются таблицы *L* и *R*.
Для их объединения, необходимо слить все строки *L* и *R*, которые соответствуют общим столбцам **x<sub>1</sub>, x<sub>2</sub>, ... x<sub>k</sub>**.
Таким образом, в результате мы получим слитые таблицы, где столбцы **x<sub>i</sub>** совпадают, а в прочих столбцах недостающая информация проставлена NULL'ами.

Сначала найдем **InnerJoin**:
* InnerJoin = π<sub>a<sub>1</sub> ... a<sub>m</sub>, b<sub>1</sub> ... b<sub>n</sub> </sub>(σ<sub>∀i A.x<sub>i</sub> = B.x<sub>i</sub></sub>(**A**×**B**))

Затем через него найдем **LeftRest** и **RightRest**:
* LeftRest = **A** \ π<sub>A</sub>(InnerJoin)
* RightRest = **B** \ π<sub>B</sub>(InnerJoin)

Таким образом, результирующая таблица **OuterJoin** будет получена следующим образом => 
**OuterJoin** = LeftRest ∪ InnerJoin ∪ RightRest

