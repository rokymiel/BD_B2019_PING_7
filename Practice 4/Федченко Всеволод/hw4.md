
# Задание №4.
## Задача 1

### а) **Какие фамилии читателей в Москве?**
```sql
SELECT DISTINCT LastName 
FROM Reader
WHERE Address LIKE '%Москва%'
```
### б) **Какие книги (author, title) брал Иван Иванов?**
```sql
SELECT DISTINCT Author, Title 
FROM Book AS bk
JOIN Reader AS r
  ON br.ReaderNr = r.ID
JOIN Borrowing as br
  ON br.ISBN = bk.ISBN
WHERE r.FirstName = 'Иван'
  AND r.LastName = 'Иванов'
```
### в) **Какие книги (ISBN) из категории "Горы" не относятся к категории "Путешествия"? Подкатегории не обязательно принимать во внимание!**
```sql
SELECT DISTINCT ISBN
FROM BookCat
WHERE CategoryName = 'Горы'
  AND ISBN NOT IN (
    SELECT ISBN
    FROM BookCat
    WHERE CategoryName = 'Путешествия'
)
```

### г) **Какие читатели (LastName, FirstName) вернули копию книгу?**
```sql
SELECT DISTINCT r.LastName, r.FirstName 
FROM Reader as r
JOIN Borrowing as br
  ON br.ReaderNr = r.ID
WHERE date(br.ReturnDate) < date('now')
```

### д) **Какие читатели (LastName, FirstName) брали хотя бы одну книгу (не копию), которую брал также Иван Иванов (не включайте Ивана Иванова в результат)?**

```sql
SELECT DISTINCT r.LastName, r.FirstName
FROM Reader AS r
JOIN Borrowing AS br 
  ON br.ReaderNr = r.ID
WHERE br.ISBN IN (
    SELECT ISBN
    FROM Borrowing
    JOIN Reader 
      ON Borrowing.ReaderNr = Reader.ID
    WHERE Reader.FirstName = 'Иван'
      AND Reader.LastName = 'Иванов'
) 
  AND (r.LastName != 'Иванов' OR r.FirstName != 'Иван');
```
P.S. 
Я забыл, можно ли использовать алиасы из query в subquery :(



## Задача 2
### а) **Найдите все прямые рейсы из Москвы в Тверь.**
```sql
SELECT DISTINCT TrainNr 
FROM Connection AS c
JOIN Station AS fr
  ON fr.Name = c.FromStation
JOIN Station AS to
  ON to.Name = c.ToStation
WHERE fr.CityName = 'Москва' 
  AND to.CityName = 'Тверь'
```

### б) **Найдите все многосегментные маршруты, имеющие точно однодневный трансфер из Москвы в Санкт-Петербург (первое отправление и прибытие в конечную точку должны быть в одну и ту же дату). Вы можете применить функцию DAY () к атрибутам Departure и Arrival, чтобы определить дату.**
```sql
SELECT DISTINCT TrainNr 
FROM Connection AS c
JOIN Station AS fr 
  ON fr.Name = c.FromStation
JOIN Station AS to 
  ON to.Name = c.ToStation
WHERE fr.CityName = 'Москва' 
  AND to.CityName = 'Санкт-Петербург' 
  AND day(Arrival) = day(Departure)
  AND TrainNr IN (
    SELECT TrainNr
    FROM Connection
    GROUP BY TrainNr
      HAVING COUNT(TrainNr) > 1
  )
```

### в) **Что изменится в выражениях для а) и б), если отношение "Connection" не содержит дополнительных кортежей для транзитивного замыкания, поэтому многосегментный маршрут Москва-> Тверь-> Санкт-Петербург содержит только кортежи Москва-> Тверь и Тверь-Санкт-Петербург?**
а)
```sql
SELECT DISTINCT c.TrainNr
FROM Connection AS c
JOIN Station AS to 
  ON to.Name = c.ToStation
WHERE to.CityName = 'Тверь'
  AND c.TrainNr IN (
	SELECT DISTINCT TrainNr 
	FROM Connection
	JOIN Station
	  ON Station.Name = Connection.FromStation
	WHERE Station.CityName = 'Москва' 
)
```
б)
```sql
WITH trains AS(
	SELECT DISTINCT TrainNr, Departure 
	FROM Connection AS c
	JOIN Station AS fr
	  ON fr.Name = c.FromStation
	WHERE fr.CityName = 'Москва' 
	  AND day(с2.Arrival) = day(с1.Departure)
	  AND с1.TrainNr IN (
		SELECT TrainNr
		FROM Connection
		GROUP BY TrainNr
		  HAVING COUNT(TrainNr) > 1
	)
)
	  
SELECT DISTINCT t.TrainNr
FROM Connection AS c
INNER JOIN trains AS t
  ON t.TrainNr = c.TrainNr
JOIN Station AS to 
  ON to.Name = c.ToStation
WHERE to.CityName = 'Санкт-Петербург' 
  AND day(c.Arrival) = day(t.Departure)
```

## Задача 3
Пусть имеются две таблицы: \
 *L = {a<sub>1</sub>, ... , a<sub>n</sub>, b<sub>1</sub>, ... , b<sub>k</sub>}* \
 *R = {b<sub>1</sub>, ... , b<sub>k</sub>, c<sub>1</sub>, ... , c<sub>m</sub>}* 

Определим Natural Join как: \
*NaturalJoin(L, R) = 𝜋<sub>{a<sub>1</sub>, ... , a<sub>n</sub>,  b<sub>1</sub>, ... , b<sub>k</sub>, , c<sub>1</sub>, ... , c<sub>m</sub>}</sub>(𝜎<sub>∀i L.b<sub>i</sub> = R.b<sub>i</sub></sub>(L × R))*

Получим остатки таблиц *L* и *R*: \
*L<sub>remainder</sub>(L, R) = L \ 𝜋<sub>{a<sub>1</sub>, ... , a<sub>n</sub>,  b<sub>1</sub>, ... , b<sub>k</sub>}</sub>(NaturalJoin(L, R)*) \
*R<sub>remainder</sub>(L, R) = R \ 𝜋<sub>{b<sub>1</sub>, ... , b<sub>k</sub>, , c<sub>1</sub>, ... , c<sub>m</sub>}</sub>(NaturalJoin(L, R)*) 

Дополним *L<sub>remainder</sub>* и *R<sub>remainder</sub>* null'ами так, чтобы их размерность была такая же, как у *NaturalJoin(L, R)* \
*L<sub>remainder</sub> = L<sub>remainder</sub> × {null, ... , null}* \
*R<sub>remainder</sub> = R<sub>remainder</sub> × {null, ... , null}*


Тогда: \
*Full OuterJoin(L, R) =  L<sub>remainder</sub>(L, R) ∪ NaturalJoin(L, R) ∪ R<sub>remainder</sub>(L, R)* \
*Left OuterJoin(L, R) =  L<sub>remainder</sub>(L, R) ∪ NaturalJoin(L, R)* \
*Right OuterJoin(L, R) = NaturalJoin(L, R) ∪ R<sub>remainder</sub>(L, R)*