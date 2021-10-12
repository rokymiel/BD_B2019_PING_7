# Задание 4
## Задача 1
SQL queries are in SQLite flavor  
a)
```sql
select LastName 
from Reader 
where Address like '%Москва%';
```
б)
```sql
select Author, Title
from Reader r, Borrowing t, Book b
where r.FirstName == 'Иван' and
      r.LastName == 'Иванов' and
      r.ID == t.ReaderNr and
      t.ISBN == b.ISBN;
``` 
в)
```sql
select ISBN
from BookCat
where BookCat.CategoryName == 'Горы' and
      BookCat.ISBN not in (select ISBN
                           from BookCat
                           where BookCat.CategoryName == 'Путешествия');
``` 
г)
```sql
select distinct LastName, FirstName
from Borrowing t, Reader r
where t.ReturnDate < '03-10-2021' and
      t.ReaderNr == r.ID;
``` 
д)
```sql
select LastName, FirstName
from Reader r, Borrowing t
where r.ID == t.ReaderNr and
      t.ISBN in ( select ISBN
                  from Reader r, Borrowing t
                  where r.ID == t.ReaderNr and
                        r.FirstName == 'Иван' and
                        r.LastName == 'Иванов') and
      r.FirstName != 'Иван' and
      r.LastName != 'Иванов';
``` 

## Задача 2
а) 
```sql
select TrainNr
from Train t
where t.StartStationName in ( select Name from Station s where s.CityName == 'Москва' ) and
      t.EndStationName in ( select Name from Station s where s.CityName == 'Тверь' );
```  
б) 
```sql
select TrainNr
from Connection c
where c.FromStation == 'Москва' and
      c.ToStation == 'Санкт-Петербург' and
      DAY(c.Departure) == DAY(c.Arrival);
```  
в) Выражение для *а* никак не поменяется, так как там требуются прямые рейса (они как раз останутся). Выражение *б* нужно будет изменить так, чтобы проверить все возможные комбинации дорог из первой точки в другую.

## Задача 3
\-