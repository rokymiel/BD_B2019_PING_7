#Пике Кирилл

## Задание 1

- Какие фамилии читателей в Москве?
```sql
selectLlastName
from Reader
where address = 'Москва';
```
- Какие книги (author, title) брал Иван Иванов?
```sql
select Author, Title
from Book
inner join Borrowing b on Book.isbn = b.isbn
inner join Reader r on b.ReaderNr = r.Id
where r.FirstName = 'Иван'
and r.LastName = 'Иванов';
```

- Какие книги (ISBN) из категории "Горы" не относятся к категории "Путешествия"?

```sql
select distinct Book.isbn
from Book
inner join BookCat bc on Book.isbn = bc.isbn
inner join Category c on bc.CategoryName = c.CategoryName
where c.CategoryName = 'Горы'
and Book.isbn not in
    (
        select distinct Book.isbn
        from Book
        inner join BookCat bc on Book.isbn = bc.isbn
        inner join Category c on bc.CategoryName = c.CategoryName
        where c.CategoryName = 'Путешествия'
    );
```

- Какие читатели (LastName, FirstName) брали хотя бы одну книгу (не копию), которую брал также Иван Иванов (не включайте Ивана Иванова в результат)?

```sql
select distinct Reader.FirstName, Reader.LastName
from Reader
inner join Borrowing b on Reader.Id = b.ReaderNr
where b.isbn in 
(
    select distinct isbn
    from Borrowing
    inner join Reader r on Borrowing.ReaderNr = r.id
    where r.FirstName = 'Иван' 
    and r.LastName = 'Иванов'
)
and Reader.FirstName <> 'Иван' and Reader.LastName <> 'Иванов';
```

- Какие читатели (LastName, FirstName) вернули копию книгу?
```sql
select Reader.FirstName, Reader.LastName
from Reader
inner join Borrowing b on Reader.Id = b.ReaderNr
where b.ReturnDate < CurrentDate;
```

## Задание 2.

- Найдите все прямые рейсы из Москвы в Тверь.
```sql
select t.TrainNr
from Train t
inner join Station StartCity
    on StartStationName = StartCity.Name
inner join Station EndCity
    on EndStationName = EndCity.Name
where StartCity.CityName = 'Москва'
and EndCity.CityName = 'Санкт-Петербург';
```

- Найдите все многосегментные маршруты, имеющие точно однодневный трансфер из Москвы в Санкт-Петербург (первое отправление и прибытие в конечную точку должны быть в одну и ту же дату).
```sql
select t.TrainNr
from Train t
inner join Station StartCity
    on StartStationName = StartCity.Name
inner join Station EndCity
    on EndStationName = EndCity.Name
inner join Connection c
    on t.StartStationName = c.FromStation
           and t.EndStationName = c.ToStation
where StartCity.CityName = 'Москва'
and EndCity.CityName = 'Санкт-Петербург'
and c.Arrival = c.Departure
and t.TrainNr in in (
    select c2.TrainNr
    from Connection c2
    group by c2.TrainNr
    having count(c2.TrainNr)>1
    );
```

- Что изменится в выражениях для а) и б), если отношение "Connection" не содержит дополнительных кортежей для транзитивного замыкания, поэтому многосегментный маршрут Москва-> Тверь-> Санкт-Петербург содержит только кортежи Москва-> Тверь и Тверь-Санкт-Петербург?

Для первого и второго запросов придется иначе искать нужную начальную и конечную точку, выясняя есть ли станиция в Мск из которой мы выезжаем и станиция в Спб, куда мы приезжаем.  
Потому что в случае Мск - Спб - Тверь мы так и не узнаем по кортежам, что есть путь Мск - Тверь  
Аналогично будет в пункте б).


