Задача 1

а) Какие фамилии читателей в Москве?
```sql
SELECT LastName FROM Reader WHERE LOWER(Reader.Address) Address LIKE "%moscow%";
```
б) Какие книги (author, title) брал Иван Иванов?
```sql
SELECT Book.Author, Book.Title 
FROM Book
INNER JOIN Borrowing
ON Book.ISBN = Borrowing.ISBN
INNER JOIN Reader 
ON Borrowing.ReaderNr = Reader.ID
AND Reader.FirstName LIKE "Иван"
AND Reader.LastName LIKE "Иванов";
```
в) Какие книги (ISBN) из категории "Горы" не относятся к категории "Путешествия"? Подкатегории не обязательно принимать во внимание!
```sql
SELECT Book.ISBN
FROM Book
INNER JOIN BookCat
ON BOOK.ISBN = BookCat.ISBN
INNER JOIN Category
ON BookCat.CategoryName = Category.CategoryName
AND Category.CategoryName LIKE "Горы"
INNER JOIN Category
ON Category.ParentCat = Category.CategoryName
AND Category.CategoryName NOT LIKE "Путешествия";
```
г) Какие читатели (LastName, FirstName) вернули копию книгу?
```sql
SELECT Reader.LastName, Reader.FirstName 
FROM Reader
INNER JOIN Borrowing
ON Reader.ID = Borrowing.ReaderNr
WHERE Borrowing.ReturnDate < NOW();
```
д) Какие читатели (LastName, FirstName) брали хотя бы одну книгу (не копию), которую брал также Иван Иванов (не включайте Ивана Иванова в результат)?
```sql
SELECT Reader.LastName, Reader.FirstName
FROM Reader 
WHER Reader.FirstName NOT LIKE "Иван"
AND Reader.LastName NOT LIKE "Иванов"
INNER JOIN Borrowing
ON Reader.ID = Borrowing.ReaderNr
INNER JOIN 
	(
	SELECT ISBN
	FROM Book
	INNER JOIN Borrowing
	ON Book.ISBN = Borrowing.ISBN
	INNER JOIN Reader 
	ON Borrowing.ReaderNr = Reader.ID
	AND Reader.FirstName LIKE "Иван"
	AND Reader.LastName LIKE "Иванов"
	)
AS IvanBooks
ON Borrowing.ISBN = IvanBooks.ISBN;
```
Задача2

а) Найдите все прямые рейсы из Москвы в Тверь. 
```sql
SELECT * FROM Connection
INNER JOIN Station
ON Connection.FromStation = Station.Name
AND Station.CityName = "Москва"
INNER JOIN Station
ON Connection.ToStation = Station.Name
AND Station.CityName = "Тверь";
```

б) Найдите все многосегментные маршруты, имеющие точно однодневный трансфер из Москвы в Санкт-Петербург (первое отправление и прибытие в конечную точку должны быть в одну и ту же дату). Вы можете применить функцию DAY () к атрибутам Departure и Arrival, чтобы определить дату. 
```sql
SELECT Train.TrainNr
(
	SELECT * FROM Connection
	INNER JOIN Station
	ON Connection.FromStation = Station.Name
	AND Station.CityName = "Москва"
)
AS Start
INNER JOIN
(
	SELECT * FROM Connection
	INNER JOIN Station
	ON Connection.FromStation = Station.Name
	AND Station.CityName = "Санкт-Петербург"
)
AS End
ON Start.TrainNr = End.TrainNr
WHERE DAY(Start.Departure) = DAY(End.Arrival)
INNER JOIN Train
ON End.TrainNr = Train.TrainNr;
```

в) Что изменится в выражениях для а) и б), если отношение "Connection" не содержит дополнительных кортежей для транзитивного замыкания, поэтому многосегментный маршрут Москва-> Тверь-> Санкт-Петербург содержит только кортежи Москва-> Тверь и Тверь-Санкт-Петербург?

В а ничего не изменится
В б придется искать не через Connection а через поезда, перебирая их