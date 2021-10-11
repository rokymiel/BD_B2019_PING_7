# Homework4
## Задача 1
### Подзадача А. Какие фамилии читателей в Москве
``` sql
SELECT LastName FROM reader
WHERE Address = 'Moscow';
```
### Подзадача Б. Какие книги (author, title) брал Иван Иванов?
``` sql
SELECT PubName, Title FROM book
INNER JOIN borrowing
ON borrowing.ISBN = book.ISBN
INNer JOIN reader
on reader.ID = borrowing.ReaderNr
WHERE  reader.LastName = 'Ivanov' and reader.FirstName = 'Ivan';
```
### Подзадача В. Какие книги (ISBN) из категории "Горы" не относятся к категории "Путешествия"? Подкатегории не обязательно принимать во внимание!
``` sql
SELECT * FROM bookcat
WHERE CategoryName = 'Mountain'
  and ISBN not in (SELECT isbn from bookcat where CategoryName = 'Travel')
```
### Подзадача Г. Какие читатели (LastName, FirstName) брали книги, которые были возвращены?
``` sql
SELECT DISTINCT reader.FirstName, reader.LastName FROM borrowing
INNER JOIN reader
ON reader.ID = borrowing.ReaderNr
WHERE ReturnDate < NOW();
```
### Подзадача Д. Какие читатели (LastName, FirstName) брали хотя бы одну книгу, которую брал также Иван Иванов (не включайте Ивана Иванова в результат)?
``` sql
SELECT DISTINCT FirstName, LastName FROM reader
INNER JOIN borrowing
ON reader.ID = borrowing.ReaderNr
where ISBN in (SELECT ISBN FROM borrowing
    JOIN reader ON reader.ID = borrowing.ReaderNr
    where reader.FirstName = 'Ivan' and reader.LastName = 'Ivanov')
AND reader.LastName != 'Ivan' and reader.FirstName != 'Ivanov';
```
## Задача 2
### Подзадача А. Найдите все прямые рейсы из Москвы в Тверь. 
``` sql
SELECT DISTINCT TrainNr FROM connection
JOIN station s1 ON s1.Name = connection.FromStation
JOIN station s2 ON s2.Name = connection.ToStation
WHERE s1.CityName = 'Moscow' AND s2.CityName = 'Tver';
```
### Подзадача Б. Найдите все многосегментные маршруты, имеющие точно однодневный трансфер из Москвы в Санкт-Петербург (первое отправление и прибытие в конечную точку должны быть в одну и ту же дату). Вы можете применить функцию DAY () к атрибутам Departure и Arrival, чтобы определить дату.
``` sql
SELECT DISTINCT TrainNr FROM connection
JOIN station s1 ON s1.Name = connection.FromStation
JOIN station s2 ON s2.Name = connection.ToStation
WHERE s1.CityName = 'Moscow' AND s2.CityName = 'Petersburg' AND (Arrival - Departure) = 0;
```
### Подзадача В. Что изменится в выражениях для а) и б), если отношение "Connection" не содержит дополнительных кортежей для транзитивного замыкания, поэтому многосегментный маршрут Москва-> Тверь-> Санкт-Петербург содержит только кортежи Москва-> Тверь и Тверь-Санкт-Петербург?
Скорее всего, пришлось бы рекурсивно вызывать джоины connection и проверять, если не нашлось совпадение, то идти дальше. 