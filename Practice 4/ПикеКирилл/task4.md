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
from Train as t
where StartStationName = 'Москва'
  and EndStationName = 'Санкт-Петербург'
  and t.TrainNr in 
  (
    select t2.TrainNr
    from Train t2
             inner join Connection on t2.TrainNr = Connection.TrainNr
    group by t2.TrainNr
    having count(t2.TrainNr) = 1
  );
```

- Найдите все многосегментные маршруты, имеющие точно однодневный трансфер из Москвы в Санкт-Петербург (первое отправление и прибытие в конечную точку должны быть в одну и ту же дату).
```sql
select distinct c.TrainNr
from Connection c
where c.FromStation='Москва' 
and c.ToStation='Санкт-Петербург'
and c.Arrival - c.Departure = 0
and c.TrainNr in (
    select c2.TrainNr
    from Connection c2
    group by c2.TrainNr
    having count(c2.TrainNr)>1
    );
```

- Что изменится в выражениях для а) и б), если отношение "Connection" не содержит дополнительных кортежей для транзитивного замыкания, поэтому многосегментный маршрут Москва-> Тверь-> Санкт-Петербург содержит только кортежи Москва-> Тверь и Тверь-Санкт-Петербург?

Для первого запроса ничего не изменится, мы все так же ищем поезд, который имеет только один кортеж от начала и до конца.  
Для второго запроса нам придется несколько иначе иначе все искать. Нам нужно отдельно найти все времена прихода в Спб, все времена отхода из Мск. Затем посчитать разницу таких времен для каждого из путей и убедиться, что для подходящих путей более 1 кортежа. Например:
```sql
select distinct out.TrainNr
from(
         select c.Departure, c.TrainNr
         from Connection c
         where c.FromStation = 'Москва'
    ) out
        inner join 
    (
        select c.Arrival, c.TrainNr
        from Connection c
        where c.ToStation = 'Санкт-Петербург'
    ) arr
        on arr.TrainNr = out.TrainNr
where out.Departure - arr.Arrival = 0
and out.TrainNr in 
(
    select c2.TrainNr
    from Connection c2
    group by c2.TrainNr
    having count(c2.TrainNr)>1
);
```


