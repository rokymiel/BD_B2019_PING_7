# Задание 6: Ткаченко Никита, БПИ197
## Задача №1

### Пункт №1

SELECT EXTRACT(YEAR FROM P.BIRTHDATE), COUNT(DISTINCT P.PLAYER_ID), COUNT(r.medal) FROM PLAYERS P
INNER JOIN results R ON P.PLAYER_ID = R.PLAYER_ID
INNER JOIN events E ON R.EVENT_ID = E.EVENT_ID
INNER JOIN olympics O ON E.OLYMPIC_ID = O.OLYMPIC_ID
WHERE R.MEDAL = 'GOLD'
AND O.year = 2004
GROUP BY EXTRACT(YEAR FROM P.BIRTHDATE)

### Пункт №2

SELECT E.EVENT_ID, E.NAME FROM EVENTS E
INNER JOIN RESULTS R on E.EVENT_ID = R.EVENT_ID
WHERE IS_TEAM_EVENT = 0 AND R.MEDAL = 'GOLD'
GROUP BY E.EVENT_ID,E.NAME
HAVING COUNT(*) >= 2

### Пункт №3

SELECT DISTINCT P.NAME, E.OLYMPIC_ID FROM PLAYERS P
INNER JOIN RESULTS R on P.PLAYER_ID = R.PLAYER_ID
INNER JOIN EVENTS E on E.EVENT_ID = R.EVENT_ID

### Пункт №4

SELECT P.COUNTRY_ID
FROM PLAYERS P
GROUP BY P.COUNTRY_ID
ORDER BY COUNT
(CASE
    WHEN P.NAME LIKE 'A%' THEN 1
    WHEN P.NAME LIKE 'a%' THEN 1
    WHEN P.NAME LIKE 'E%' THEN 1
    WHEN P.NAME LIKE 'e%' THEN 1
    WHEN P.NAME LIKE 'I%' THEN 1
    WHEN P.NAME LIKE 'i%' THEN 1
    WHEN P.NAME LIKE 'O%' THEN 1
    WHEN P.NAME LIKE 'o%' THEN 1
    WHEN P.NAME LIKE 'U%' THEN 1
    WHEN P.NAME LIKE 'u%' THEN 1
END) * 100 / COUNT(*) DESC
LIMIT 1

### Пункт №5

SELECT C.NAME, C.POPULATION FROM OLYMPICS O
INNER JOIN EVENTS E on O.OLYMPIC_ID = E.OLYMPIC_ID
INNER JOIN RESULTS R on E.EVENT_ID = R.EVENT_ID
INNER JOIN PLAYERS P on R.PLAYER_ID = P.PLAYER_ID
INNER JOIN COUNTRIES C on C.COUNTRY_ID = P.COUNTRY_ID
WHERE O."year" = 2000 AND E.IS_TEAM_EVENT = 1
GROUP BY C.NAME, C.POPULATION
ORDER BY COUNT(R.MEDAL) / C.POPULATION
LIMIT 5
