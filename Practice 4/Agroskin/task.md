# Задание 4: Агроскин Александр, БПИ197
Используется диалект SQLite
# №1:
a) Какие фамилии читателей в Москве?
```sql
SELECT DISTINCT LastName FROM Reader
WHERE Address LIKE '%Москва%'
```

б) Какие книги (author, title) брал Иван Иванов?
```sql
SELECT DISTINCT Author, Title
FROM Book
         JOIN Borrowing
              ON Book.ISBN = Borrowing.ISBN
         JOIN Reader
              ON Borrowing.ReaderNr = Reader.ID
WHERE Reader.LastName = 'Иванов'
  AND Reader.FirstName = 'Иван'
```

в) Какие книги (ISBN) из категории "Горы" не относятся к категории "Путешествия"? Подкатегории не обязательно принимать во внимание!
```sql
SELECT DISTINCT BookCat.ISBN
FROM BookCat
WHERE CategoryName = 'Горы'
    EXCEPT
SELECT DISTINCT BookCat.ISBN
FROM BookCat
WHERE CategoryName = 'Путешествия'
```

г) Какие читатели (LastName, FirstName) вернули копию книгу?
```sql
SELECT DISTINCT Reader.FirstName, Reader.LastName
FROM Reader
         JOIN Borrowing
              ON Borrowing.ReaderNr = Reader.ID
WHERE Borrowing.ReturnDate <= DATE('now')
```

д) Какие читатели (LastName, FirstName) брали хотя бы одну книгу (не копию), которую брал также Иван Иванов (не включайте Ивана Иванова в результат)?
```sql
SELECT DISTINCT Reader.FirstName, Reader.LastName
FROM Borrowing
         JOIN Reader
              ON Reader.ID = Borrowing.ReaderNr
WHERE Borrowing.ISBN IN (SELECT Borrowing.ISBN
                         FROM Borrowing
                                  JOIN Reader
                                       ON Reader.ID = Borrowing.ReaderNr
                         WHERE Reader.FirstName = 'Иван'
                           AND Reader.LastName = 'Иванов')
  AND (Reader.FirstName != 'Иван' OR Reader.LastName != 'Иванов')
```

# №2:
а) Найдите все прямые рейсы из Москвы в Тверь.
```sql
SELECT DISTINCT Connection.TrainNr, Connection.Departure, Connection.Arrival
FROM Connection
WHERE Connection.FromStation = 'Москва'
  AND Connection.ToStation = 'Тверь'
```

б) Найдите все многосегментные маршруты, имеющие точно однодневный трансфер из Москвы в Санкт-Петербург (первое отправление и прибытие в конечную точку должны быть в одну и ту же дату).
```sql
SELECT DISTINCT *
FROM Train
         JOIN
     (SELECT Connection.TrainNr, Connection.Departure, Connection.Arrival
      FROM Connection
      GROUP BY Connection.TrainNr, Connection.Departure, Connection.Arrival
      HAVING COUNT(*) > 1) MultiSegment
     ON MultiSegment.TrainNr = Train.TrainNr
WHERE DAY(MultiSegment.Departure) = DAY(MultiSegment.Arrival)
```

в) Что изменится в выражениях для а) и б), если отношение "Connection" не содержит дополнительных кортежей для транзитивного замыкания?

Транзитивное замыкание невозможно выразить через реляционную алгебру (а значит и SQL, если не прибегать к встроенным функциям), но можно получить его аппроксимацию, заджойнив `Connection` с собой столько раз, какова длина максимального пути. С помощью этого приближения можно делать те же самые операции, что и в задачах а), б) с транзитивным замыканием.

# №3:
Представьте внешнее объединение (outer join ) в виде выражения реляционной алгебры с использованием только базовых операций (select, project, cartesian, rename, union, minus)

Как я понял имеется в виду Full Outer Join, но в любом случае разница не очень большая.

Пусть нам даны две таблицы **A** и **B**. Для того, чтобы выполнить Full Outer Join по некоторым общим столбцам **x<sub>1</sub>, x<sub>2</sub>, ... x<sub>k</sub>**, нам требуется слить все строки **A** и **B** где все столбцы **x<sub>i</sub>** совпадают, а недостающие значения в прочих столбцах заполнить null'ами в соответствии с таблицей.

Итоговая таблица **R** будет получена объединением трех множеств:
1. InnerJoin = π<sub>a<sub>1</sub> ... a<sub>m</sub>, b<sub>1</sub> ... b<sub>n</sub> </sub>(σ<sub>∀i A.x<sub>i</sub> = B.x<sub>i</sub></sub>(**A**×**B**))
2. LeftRemainder = **A** \ π<sub>A</sub>(InnerJoin) (считаю, что неспроецированные столбцы и являются определением null'a)
3. RightRemainder = **B** \ π<sub>B</sub>(InnerJoin)

Таким образом, R = LeftRemainder ∪ InnerJoin ∪ RightRemainder 






















