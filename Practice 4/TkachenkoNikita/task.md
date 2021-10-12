# Задание 4: Ткаченко Никита, БПИ197
## Задача №1:
* a) SELECT LASTNAME FROM READER 
     WHERE ADDRESS LIKE '%Moscow%'
* б) SELECT AUTHOR, TITLE FROM BOOK book
        INNER JOIN BORROWING bor
            ON book.ISBN = bor.ISBN
        INNER JOIN READER r
            ON r.ID = bor.READERNR
     WHERE r.LASTNAME = 'Иванов' AND r.FIRSTNAME = 'Иван'
* в) SELECT book.ISBN FROM BOOK book
        INNER JOIN BOOKCAT bc
            ON book.ISBN = bc.ISBN
     WHERE bc.CATEGORYNAME = 'Горы' AND bc.CATEGORYNAME != 'Путешествия'
* г) SELECT LASTNAME, FIRSTNAME FROM READER r
        INNER JOIN BORROWING b
            ON r.ID = b.READERNR
     WHERE b.RETURNDATE < CURRENT DATE
* д) SELECT DISTINCT LASTNAME, FIRSTNAME FROM READER r
        INNER JOIN BORROWING b ON r.ID = b.READERNR
     WHERE ISBN IN
        (SELECT DISTINCT ISBN FROM READER r
        INNER JOIN BORROWING b on r.ID = b.READERNR
        WHERE LASTNAME = 'Иванов'
        AND FIRSTNAME = 'Иван')
     AND NOT (LASTNAME = 'Иванов' AND FIRSTNAME = 'Иван')
     
## Задача №2:
* a) SELECT tr.TRAINNR FROM TRAIN tr
     INNER JOIN CONNECTED c on tr.TRAINNR = c.TRAINNR
     WHERE c.FROMSTATIONNAME in (SELECT STATIONNAME FROM STATION WHERE CITYNAME = 'Москва')
     AND c.TOSTATIONNAME in (SELECT STATIONNAME FROM STATION WHERE CITYNAME = 'Тверь')
       AND tr.TrainNr in (
         SELECT TrainNr
         FROM CONNECTED
         GROUP BY CONNECTED.TrainNr
         HAVING COUNT(*) = 1
     )
* б) SELECT tr.TRAINNR FROM TRAIN tr
     INNER JOIN CONNECTED c on tr.TRAINNR = c.TRAINNR
     WHERE c.FROMSTATIONNAME in (SELECT STATIONNAME FROM STATION WHERE CITYNAME = 'Москва')
     AND c.TOSTATIONNAME in (SELECT STATIONNAME FROM STATION WHERE CITYNAME = 'Санкт-Петербург')
     AND Day(c.Arrival) = Day(c.Departure)
     AND c.TrainNr in (
     SELECT TrainNr
     FROM CONNECTED
     GROUP BY TrainNr
     HAVING COUNT(*) > 1
     );
* в) Для пункта а) не пришлось бы добавлять проверку на транзитивное замыкание в последнем and. Что упростило бы поиск простых маршрутов. Для пункта б) транзитивность упрощает поиск связей между маршрутами и нам не приходится это делать вручную
