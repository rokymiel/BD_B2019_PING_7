## Задание 4

В задании используется диалект PostgreSQL.

### Задача 1

а) Какие фамилии читателей в Москве?\
```sql
SELECT LAST_NAME
FROM READER
WHERE ADDRESS LIKE '%Москва%'
```

б) Какие книги (author, title) брал Иван Иванов?\
```sql
SELECT DISTINCT AUTHOR, TITLE
FROM BOOK
INNER JOIN BORROWING ON BOOK.ISBN = BORROWING.ISBN
INNER JOIN READER ON BORROWING.READER_NR = READER.ID
WHERE FIRST_NAME = 'Иван'
                AND LAST_NAME = 'Иванов'
```

в) Какие книги (ISBN) из категории "Горы" не относятся к категории "Путешествия"?
```sql
SELECT BOOK.ISBN
FROM BOOK
         INNER JOIN BOOKCAT ON BOOK.ISBN = BOOKCAT.ISBN
WHERE CATEGORY_NAME = 'Горы'
  AND CATEGORY_NAME != 'Путешествия'
```

г) Какие читатели (LastName, FirstName) вернули копию книги?
```sql
SELECT LAST_NAME,
	FIRST_NAME
FROM READER
INNER JOIN BORROWING ON READER.ID = BORROWING.READER_NR
WHERE RETURN_DATE IS NOT NULL
				AND DATE(RETURN_DATE) < DATE('now')
```

д) Какие читатели (LastName, FirstName) брали хотя бы одну 
книгу (не копию), которую брал также Иван Иванов 
(не включайте Ивана Иванова в результат)?

```sql
SELECT LAST_NAME,
	FIRST_NAME
FROM READER
INNER JOIN BORROWING ON READER.ID = BORROWING.READER_NR
WHERE ISBN in
			(SELECT BORROWING.ISBN
				FROM BORROWING
				INNER JOIN READER ON READER.ID = BORROWING.READER_NR
				WHERE FIRST_NAME = 'Иван'
								AND LAST_NAME = 'Иванов')
	  AND FIRST_NAME != 'Иван'
	  AND LAST_NAME != 'Иванов'
```

### Задача 2

а) Найдите все прямые рейсы из Москвы в Тверь.
```sql
SELECT TRAIN.TRAIN_NR
FROM TRAIN
INNER JOIN CONNECTION ON TRAIN.TRAIN_NR = CONNECTION.TRAIN_NR
WHERE FROM_STATION = 'Москва'
				AND TO_STATION = 'Тверь'
```

б) Найдите все многосегментные маршруты, имеющие точно 
однодневный трансфер из Москвы в Санкт-Петербург (первое 
отправление и прибытие в конечную точку должны быть в одну 
и ту же дату). Вы можете применить функцию DAY () к 
атрибутам Departure и Arrival, чтобы определить дату.
```sql
SELECT CONNECTION.TRAIN_NR
FROM CONNECTION
INNER JOIN STATION AS S1 ON CONNECTION.FROM_STATION = S1.NAME
INNER JOIN STATION AS S2 ON CONNECTION.TO_STATION = S2.NAME
WHERE TRAIN_NR IN
								(SELECT TRAIN_NR
									FROM CONNECTION
									GROUP BY TRAIN_NR
									HAVING COUNT(TRAIN_NR) > 1)
				AND S1.NAME = 'Москва'
				AND S2.NAME = 'Санкт-Петербург'
				AND DAY(ARRIVAL) = DAY(DEPARTURE)
```

в) Что изменится в выражениях для а) и б), если отношение "Connection" не содержит дополнительных кортежей для транзитивного замыкания, поэтому многосегментный маршрут Москва-> Тверь-> Санкт-Петербург содержит только кортежи Москва-> Тверь и Тверь-Санкт-Петербург?


Пункт а) не изменится, так как там мы не имеем дело с многосегментными маршрутами.\
В б) нужно будет искать начало и конец многосегментного маршрута через цепочку connection-ов, джоиня их друг на друга.