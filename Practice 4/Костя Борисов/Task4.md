# Задание №4. Борисов Костя БПИ197
## Задача 1

```sql
CREATE TABLE Reader(ID, LastName, FirstName, Address, BirthDate);
CREATE TABLE Book(ISBN, Title, Author, PagesNum, PubYear, PubName);
CREATE TABLE Publisher(PubName, PubAdress);
CREATE TABLE Category(CategoryName, ParentCat);
CREATE TABLE Copy(ISBN, CopyNumber, ShelfPosition);

CREATE TABLE Borrowing(ReaderNr, ISBN, CopyNumber, ReturnDate);
CREATE TABLE BookCat(ISBN, CategoryName);
```

### Запрос 1
Какие фамилии читателей в Москве?

```sql
SELECT LastName FROM Reader WHERE Address LIKE '%Москва%'
```

### Запрос 2
Какие книги (author, title) брал Иван Иванов?

```sql
SELECT DISTINCT Author, Title FROM Book
JOIN Borrowing ON Borrowing.ISBN = Book.ISBN
JOIN Reader ON Borrowing.ReaderNr = Reader.ID
WHERE Reader.FirstName = 'Иван' AND Reader.LastName = 'Иванов'
```

### Запрос 3
Какие книги (ISBN) из категории "Горы" не относятся к категории "Путешествия"?
Подкатегории не обязательно принимать во внимание!

```sql
SELECT DISTINCT ISBN FROM BookCat
WHERE CategoryName = 'Горы'
EXCEPT
SELECT DISTINCT ISBN FROM BookCat
WHERE CategoryName = 'Путешествия'
```

### Запрос 4
Какие читатели (LastName, FirstName) брали книги, которые были возвращены?

```sql
SELECT DISTINCT Reader.LastName, Reader.FirstName FROM Reader
JOIN Borrowing ON Borrowing.ReaderNr = Reader.ID
WHERE date(Borrowing.ReturnDate) < date('now')
```

### Запрос 5
Какие читатели (LastName, FirstName) брали хотя бы одну книгу,
которую брал также Иван Иванов (не включайте Ивана Иванова в результат)?

```sql
SELECT DISTINCT Reader.LastName, Reader.FirstName FROM Borrowing
JOIN Reader ON Borrowing.ReaderNr = Reader.ID
WHERE Borrowing.ISBN IN (
  SELECT Borrowing.ISBN FROM Borrowing
  JOIN Reader ON Borrowing.ReaderNr = Reader.ID
  WHERE Reader.FirstName = 'Иван' AND Reader.LastName = 'Иванов'
) AND (Reader.FirstName != 'Иван' OR Reader.LastName != 'Иванов')
```

## Задача 2

```sql
CREATE TABLE City(Name, Region);
CREATE TABLE Station(Name, ♯Tracks, CityName, Region);
CREATE TABLE Train(TrainNr, Length, StartStationName, EndStationName);
CREATE TABLE Connection(FromStation, ToStation, TrainNr, Departure, Arrival);
```

### Запрос 1
Найдите все прямые рейсы из Москвы в Тверь.

```sql
SELECT DISTINCT TrainNr FROM Connection
JOIN Station s1 ON s1.Name = Connection.FromStation
JOIN Station s2 ON s2.Name = Connection.ToStation
WHERE s1.CityName = 'Москва' AND s2.CityName = 'Тверь'
EXCEPT
SELECT DISTINCT TrainNr FROM Connection
JOIN Station s1 ON s1.Name = Connection.FromStation
JOIN Station s2 ON s2.Name = Connection.ToStation
WHERE s1.CityName != 'Москва' OR s2.CityName != 'Тверь'
```

### Запрос 2
Найдите все многосегментные маршруты, имеющие точно однодневный трансфер из Москвы в Санкт-Петербург
(первое отправление и прибытие в конечную точку должны быть в одну и ту же дату).
Вы можете применить функцию DAY() к атрибутам Departure и Arrival, чтобы определить дату.

```sql
SELECT DISTINCT TrainNr FROM Connection
JOIN Station s1 ON s1.Name = Connection.FromStation
JOIN Station s2 ON s2.Name = Connection.ToStation
WHERE s1.CityName = 'Москва' AND s2.CityName = 'Санкт-Петербург' AND day(Arrival) = day(Departure)
INTERSECT
SELECT DISTINCT TrainNr FROM Connection
JOIN Station s1 ON s1.Name = Connection.FromStation
JOIN Station s2 ON s2.Name = Connection.ToStation
WHERE s1.CityName != 'Москва' OR s2.CityName != 'Санкт-Петербург'
```

### Запрос 3
Что изменится в выражениях для а) и б), если отношение "Connection" не содержит дополнительных кортежей для транзитивного замыкания,
поэтому многосегментный маршрут Москва -> Тверь -> Санкт-Петербург содержит только кортежи Москва -> Тверь и Тверь -> Санкт-Петербург?

```sql
SELECT DISTINCT TrainNr FROM Connection
JOIN Station s1 ON s1.Name = Connection.FromStation
JOIN Station s2 ON s2.Name = Connection.ToStation
WHERE s1.CityName = 'Москва' AND s2.CityName = 'Тверь'
```

```sql
SELECT DISTINCT c1.TrainNr FROM Connection c1
JOIN Connection c2 ON c2.FromStation = c1.ToStation AND c2.TrainNr = c1.TrainNr
JOIN Station s1 ON s1.Name = c1.FromStation
JOIN Station s2 ON s2.Name = c2.ToStation
WHERE s1.CityName = 'Москва' AND s2.CityName = 'Санкт-Петербург' AND day(c2.Arrival) = day(c1.Departure)
```

## Задача 3
Представьте внешнее объединение (outer join) в виде выражения реляционной алгебры с использованием только базовых операций
(select, project, cartesian, rename, union, minus)

Имееются таблицы *L* и *R*.
Они объединяются по колонкам множества *C = {c<sub>1</sub>, ... , c<sub>k</sub>}*.
Их можно записать как *L = {a<sub>1</sub>, ... , a<sub>n</sub>, c<sub>1</sub>, ... , c<sub>k</sub>}* и *R = {b<sub>1</sub>, ... , b<sub>m</sub>, c<sub>1</sub>, ... , c<sub>k</sub>}*.
Для проэкции обединения можно определить ещё одно можество *D = {a<sub>1</sub>, ... , a<sub>n</sub>, c<sub>1</sub>, ... , c<sub>k</sub>, b<sub>1</sub>, ... , b<sub>m</sub>}*: список всех колонок из *L* и *R* с колонкоми *C* наложенными друг на друга. Нам также понадобится функция, сравнивающая равенство колонок C: *Sel(L × R) =* ⋀<sub>*i*</sub> (*L.b<sub>i</sub> = R.b<sub>i</sub>*).

Тогда NaturalJoin = 𝜋<sub>*D*</sub>(𝜎<sub>*Sel(L × R)*</sub>(*L × R*)). \
Теперь в него надо добавить записи, которые в него не попали.

Сначала найдём те строки из *L*, которые непопали в NaturalJoin, а потом дозаполним их NULL-ями. \
LeftRest = (*L* - 𝜋<sub>*L*</sub>(NaturalJoin)) × *{(null, ..., null)}* \
RightRest = *{(null, ..., null)}* × (*R* - 𝜋<sub>*R*</sub>(NaturalJoin))

Теперь осталось всё объединить вместе. \
OuterJoin = NaturalJoin ∪ RightRest ∪ LeftRest

