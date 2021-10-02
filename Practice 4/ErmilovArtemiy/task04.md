# Задание 1.

а) Какие фамилии читателей в Москве?
```sql
select Reader.LastName
from Reader
where Reader.Address like '%Москва%';
```
б) Какие книги (author, title) брал Иван Иванов?   

```sql
select distinct Book.Author, Book.Title
from Borrowing inner join Book on Borrowing.ISBN = Book.ISBN
where Borrowing.ReaderNr = (
    select Reader.ID 
    from Reader  
    where Reader.LastName = 'Иванов' and Reader.FirstName = 'Иван'
);
```

в) Какие книги (ISBN) из категории "Горы" не относятся к категории "Путешествия"? Подкатегории не обязательно принимать во внимание!
```sql
select distinct BookCat.ISBN
from BookCat
where BookCat.CategoryName = 'Горы'
except
select distinct BookCat.ISBN
from BookCat
where BookCat.CategoryName = 'Путешествия';
```

г) Какие читатели (LastName, FirstName) вернули копию книгу?
```sql
select distinct Reader.LastName, Reader.FirstName
from Reader
where Reader.ID in (select Borrowing.ReaderNr
                    from Borrowing
                    where Borrowing.ReturnDate < datetime()
);
```

д) Какие читатели (LastName, FirstName) брали хотя бы одну книгу (не копию), которую брал также Иван Иванов (не включайте Ивана Иванова в результат)?
```sql
select distinct Reader.LastName, Reader.FirstName
from Borrowing left join Reader on Borrowing.ReaderNr = Reader.ID
where Borrowing.ISBN in (
    select distinct Borrowing.ISBN
    from Borrowing
    where Borrowing.ReaderNr in (
        select Reader.ID
        from Reader
        where Reader.LastName = 'Иванов'
          and Reader.FirstName = 'Иван'
    )
) and not (FirstName = 'Иван' and LastName = 'Иванов');
```

# Задание 2

а) Найдите все прямые рейсы из Москвы в Тверь.

__Считаем прямыми те рейсы, у которых начало маршрута в Москве, а конец - в Твери.__
```sql
select Train.TrainNr
from Train
where Train.StartStationName = 'Москва'
  and EndStationName = 'Санкт-Петербург'
  and Train.TrainNr in (
    select Connection.TrainNr
    from Connection
    group by Connection.TrainNr
    having count(Connection.TrainNr) = 1
);
```

б) Найдите все многосегментные маршруты, имеющие точно однодневный трансфер из Москвы в Санкт-Петербург (первое отправление и прибытие в конечную точку должны быть в одну и ту же дату). Вы можете применить функцию DAY () к атрибутам Departure и Arrival, чтобы определить дату.
```sql
select distinct Connection.TrainNr
from Connection
where Connection.FromStation = 'Москва'
  and Connection.ToStation = 'Санкт-Петербург'
  and julianday(Connection.Arrival) = julianday(Connection.Departure)
  and Connection.TrainNr in (
    select Connection.TrainNr
    from Connection
    group by Connection.TrainNr
    having count(Connection.TrainNr) > 1
);
```

в) Что изменится в выражениях для а) и б), если отношение "Connection" не содержит дополнительных кортежей для транзитивного замыкания, поэтому многосегментный маршрут Москва-> Тверь-> Санкт-Петербург содержит только кортежи Москва-> Тверь и Тверь-Санкт-Петербург?

а) останется таким же.

Измененный б) :
```sql
select distinct dep.TrainNr
from (
         select Connection.Departure, Connection.TrainNr
         from Connection
         where Connection.FromStation = 'Москва'
     ) dep inner join (
        select Connection.Arrival, Connection.TrainNr
        from Connection
        where Connection.ToStation = 'Санкт-Петербург'
    ) arr on dep.TrainNr = arr.TrainNr
where julianday(dep.Departure) = julianday(arr.Arrival)
  and dep.TrainNr in (
    select Connection.TrainNr
    from Connection
    group by Connection.TrainNr
    having count(Connection.TrainNr) > 1
);
```
