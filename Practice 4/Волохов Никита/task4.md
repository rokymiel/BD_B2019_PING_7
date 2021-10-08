# Курс "Базы данных"

## Домашняя работа 4. Волохов Никита Алексеевич, БПИ197

### **_Задача 1._**
**а) Какие фамилии читателей в Москве?**
```sql
SELECT LastName FROM Reader WHERE Address LIKE '%Москва%'
```
**б) Какие книги (author, title) брал Иван Иванов?**
```sql
SELECT Book.Author, Book.Title 
FROM Book JOIN Borrowing ON Borrowing.ISBN = Book.ISBN
          JOIN Reader ON Borrowing.ReaderNr = Reader.ID
WHERE Reader.LastName = "Иванов" AND Reader.FirstName = "Иван"
```
**в) Какие книги (ISBN) из категории "Горы" не относятся к категории "Путешествия"? Подкатегории не обязательно принимать во внимание!**
```sql
SELECT ISBN FROM BookCat WHERE CategoryName = "Горы"
EXCEPT
SELECT ISBN FROM BookCat WHERE CategoryName = "Путешествия"
```
**г) Какие читатели (LastName, FirstName) вернули копию книгу?**
```sql
SELECT Reader.LastName, Reader.FirstName 
FROM Reader JOIN Borrowing ON Reader.ID = Borrowing.ReaderNr
WHERE DATE(Borrowing.ReturnDate) < DATE('now')
```
**д) Какие читатели (LastName, FirstName) брали хотя бы одну книгу (не копию), которую брал также Иван Иванов (не включайте Ивана Иванова в результат)?**
```sql
SELECT Reader.LastName, Reader.FirstName 
FROM Reader JOIN Borrowing ON Reader.ID = Borrowing.ReaderNr
WHERE Borrowing.ISBN IN (
    SELECT Borrowing.ISBN FROM Borrowing
    JOIN Reader ON Reader.ID = Borrowing.ReaderNr
    WHERE Reader.LastName = "Иванов" AND Reader.FirstName = "Иван"
) AND Reader.LastName != "Иванов" AND Reader.FirstName != "Иван"
```

### **_Задача 2._** 
**a) Найдите все прямые рейсы из Москвы в Тверь.**
```sql
SELECT Connection.TrainNr 
FROM Connection JOIN Station AS SFrom ON Connection.FromStation = SFrom.Name
                JOIN Station AS STo ON Connection.FromStation = STo.Name
WHERE SFrom.CityName = 'Москва' AND STo.CityName = 'Тверь'
```
**б) Найдите все многосегментные маршруты, имеющие точно однодневный трансфер из Москвы в Санкт-Петербург (первое отправление и прибытие в конечную точку должны быть в одну и ту же дату). Вы можете применить функцию DAY () к атрибутам Departure и Arrival, чтобы определить дату.**
```sql
SELECT Connection.TrainNr
FROM Connection JOIN Station AS SFrom ON Connection.FromStation = SFrom.Name
                JOIN Station AS STo ON Connection.ToStation = STo.Name
WHERE SFrom.CityName = 'Москва' AND STo.CityName = 'Санкт-Петербург' AND DAY(Connection.Departure) = DAY(Connection.Arrival)
AND Connection.TrainNr IN (
    SELECT TrainNr FROM Connection
    GROUP BY TrainNr
    HAVING COUNT(TrainNr) > 1
)
```
**в) Что изменится в выражениях для а) и б), если отношение "Connection" не содержит дополнительных кортежей для транзитивного замыкания, поэтому многосегментный маршрут Москва-> Тверь-> Санкт-Петербург содержит только кортежи Москва-> Тверь и Тверь-Санкт-Петербург?**

a)
```sql
SELECT TrainNr 
FROM Train JOIN Connection AS Con1 ON Train.TrainNr = Con1.TrainNr
           JOIN Connection AS Con2 ON Train.TrainNr = Con2.TrainNr
           JOIN Station AS St1 ON Con1.FromStation = Train.StartStationName
           JOIN Station AS St2 ON Con2.FromStation = Train.EndStationName
WHERE St1.CityName = 'Москва' AND St2.CityName = 'Тверь'
```

б)
```sql
SELECT Con1.TrainNr 
FROM Train JOIN Connection AS Con1 ON Train.TrainNr = Con1.TrainNr
           JOIN Connection AS Con2 ON Train.TrainNr = Con2.TrainNr
           JOIN Station AS St1 ON Con1.FromStation = Train.StartStationName
           JOIN Station AS St2 ON Con2.FromStation = Train.EndStationName
WHERE St1.CityName = 'Москва' AND St2.CityName = 'Санкт-Петербург' AND DAY(Con1.Departure) = DAY(Con2.Arrival)
AND Con1.TrainNr IN (
    SELECT TrainNr FROM Connection
    GROUP BY TrainNr
    HAVING COUNT(TrainNr) > 1
)
```

### **_Задача 3._**
**Представьте внешнее объединение (outer join) в виде выражения реляционной алгебры с использованием только базовых операций (select, project, cartesian, rename, union, minus)**

Пусть существуют отношения _L = {l<sub>1</sub>, ..., l<sub>m</sub>, c<sub>1</sub>, ..., c<sub>l</sub>}_ и _R = {c<sub>1</sub>, ..., c<sub>l</sub>, r<sub>1</sub>, ..., r<sub>n</sub>}_. Пусть у отношений _L_ и _R_ существуют общие колонки _C = {c<sub>1</sub>, ..., c<sub>l</sub>}_, по которым они будут объединяться.

Тогда _NaturalJoin(L, R) = π<sub>l<sub>1</sub></sub>, ..., <sub>l<sub>m</sub></sub>, <sub>c<sub>1</sub></sub>, ..., <sub>c<sub>l</sub></sub>, <sub>r<sub>1</sub></sub>, ..., <sub>r<sub>n</sub></sub>($\sigma$<sub>L.C<sub>1</sub> = R.C<sub>1</sub></sub>, ..., <sub>L.C<sub>l</sub> = R.C<sub>l</sub></sub>(L x R))_. (Тоже самое, что и Inner Join в SQL)

Теперь необходимо найти строки, которые не остались после выполнения _NaturalJoin(L, R)_.

Тогда _RestOfRowsLeft(L, R) = (L \ π<sub>L</sub>(NaturalJoin(L, R))) x {(null<sub>1</sub>, ..., null<sub>n</sub>)}_.

Тогда _RestOfRowsRight(L, R) = {(null<sub>1</sub>, ..., null<sub>m</sub>)} x (R \ π<sub>R</sub>(NaturalJoin(L, R)))_.

Тогда _OuterJoin(L, R) = NaturalJoin(L, R) V RestOfRowsLeft(L, R) V RestOfRowsRight(L, R)_.