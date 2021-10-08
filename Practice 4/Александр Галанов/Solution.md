# Курс "Базы данных"

## Задание 4
 
### задача 1
SQL-запросы

* SELECT LASTNAME
FROM READER
WHERE ADDRESS LIKE '%Москва%'
   OR ADDRESS LIKE '%Moscow%';


* SELECT AUTHOR, TITLE
FROM BOOK
         INNER JOIN BORROWING ON BOOK.ISBN = BORROWING.ISBN
         INNER JOIN READER ON READER.READER_ID = BORROWING.READERNR
WHERE LASTNAME = 'Иванов'
  AND FIRSTNAME = 'Иван';


* SELECT ISBN
FROM BOOKCAT
WHERE CATEGORYNAME = 'Горы' EXCEPT
SELECT ISBN
FROM BOOKCAT
WHERE CATEGORYNAME = 'Путешествия';


* SELECT DISTINCT LASTNAME, FIRSTNAME, READER_ID
FROM READER
         INNER JOIN BORROWING ON READER.READER_ID = BORROWING.READERNR
WHERE RETURNDATE IS NOT NULL;


* SELECT DISTINCT LASTNAME, FIRSTNAME, READER_ID
FROM READER
         INNER JOIN BORROWING ON READER.READER_ID = BORROWING.READERNR
WHERE ISBN IN
      (SELECT ISBN
       FROM READER
                INNER JOIN BORROWING ON READER.READER_ID = BORROWING.READERNR
       WHERE LASTNAME = 'Иванов'
         AND FIRSTNAME = 'Иван')
  AND READER_ID !=
      (SELECT READER_ID FROM READER WHERE LASTNAME = 'Иванов' AND FIRSTNAME = 'Иван');
### задача 2

* SELECT TRAINNR
FROM TRAIN
WHERE TRAINNR NOT IN
      (SELECT DISTINCT TRAIN.TRAINNR
       FROM TRAIN
                INNER JOIN CONNECTED ON TRAIN.TRAINNR = CONNECTED.TRAINNR
       WHERE (STARTSTATIONNAME != FROMSTATIONNAME OR ENDSTATIONNAME != TOSTATIONNAME))
  AND STARTSTATIONNAME in (SELECT STATIONNAME FROM STATION WHERE CITYNAME = 'Москва')
  AND ENDSTATIONNAME in (SELECT STATIONNAME FROM STATION WHERE CITYNAME = 'Тверь');

* SELECT TRAINNR
FROM CONNECTED
WHERE TRAINNR IN
      (SELECT DISTINCT TRAIN.TRAINNR
       FROM TRAIN
                INNER JOIN CONNECTED ON TRAIN.TRAINNR = CONNECTED.TRAINNR
       WHERE (STARTSTATIONNAME != FROMSTATIONNAME OR ENDSTATIONNAME != TOSTATIONNAME))
  AND (FROMSTATIONNAME in (SELECT STATIONNAME FROM STATION WHERE CITYNAME = 'Москва')
    AND TOSTATIONNAME in (SELECT STATIONNAME FROM STATION WHERE CITYNAME = 'Санкт-Петербург'))
  AND Day(ARRIVAL) = Day(DEPARTURE);
  
* Если бы не было транзитивного замыкания, то нам бы не пришлось для первого пункта сначала искать множетсво многосегментных рейсов (TrainNRs входящие в данное множество), и потом вычлинять его из всего множества рейсов, тем самым оставлять только правдивые прямые рейсы.
Мы могли просто соединить две таблицы, и отобрать те маршруты для которых выполняется условие: STARTSTATIONNAME = FROMSTATIONNAME AND ENDSTATIONNAME = TOSTATIONNAME
* Для пункта б ситуация обратная, потому что транзитивность позволяет обрабатывать составные маршруты вида (Москва-> Тверь-> Питер) как (Москва -> Питер), благодаря чему, мы можем достаточно просто применить условие на длительность поездки и тем самым отобрать маршруты
Если бы не было транзитивного замыкания, то после нахождения всех составных маршрутов, нам надо было найти соединения в одном маршруте, которые составляют маршрут (Москва->...->Питер) и сравнить длительность

### задача 3
