# Задание №6. Кочарян Тигран БПИ197

## Запрос 1
Для Олимпийских игр 2004 года сгенерируйте список (год рождения, количество игроков, количество золотых медалей),
содержащий годы, в которые родились игроки, количество игроков, родившихся в каждый из этих лет,
которые выиграли по крайней мере одну золотую медаль, и количество золотых медалей, завоеванных игроками, родившимися в этом году.

```sql
SELECT
  DATE_PART('year', Players.birthdate) as byear,
  COUNT(DISTINCT Players.player_id),
  COUNT(Results.medal)
    FROM Olympics
JOIN Events 
  ON Events.olympic_id = Olympics.olympic_id
JOIN Results 
  ON Events.event_id = Results.event_id
JOIN Players 
  ON Players.player_id = Results.player_id
WHERE year = 2004 
  AND Results.medal = 'GOLD' 
GROUP BY 1;
```

## Запрос 2
Перечислите все индивидуальные (не групповые) соревнования, в которых была ничья в счете, и два или более игрока выиграли золотую медаль.

```sql
SELECT Events.event_id 
  FROM Events
JOIN Results 
  ON Events.event_id = Results.event_id
WHERE is_team_event = 0 
  AND Results.medal = 'GOLD'
GROUP BY Events.event_id 
  HAVING COUNT(Results.medal) >= 2;
```

## Запрос 3
Найдите всех игроков, которые выиграли хотя бы одну медаль (GOLD, SILVER и BRONZE) на одной Олимпиаде. (player-name, olympic-id).

```sql
SELECT DISTINCT Players.name, Events.olympic_id 
  FROM Players
JOIN Results 
  ON Results.player_id = Players.player_id
JOIN Events 
  ON Events.event_id = Results.event_id
WHERE Results.medal 
  IN ('BRONZE', 'SILVER', 'GOLD');
```

## Запрос 4
В какой стране был наибольший процент игроков (из перечисленных в наборе данных), чьи имена начинались с гласной?
```sql
SELECT leader_first.country_id, cast(vowels as decimal)/total FROM (
  SELECT Players.country_id, COUNT(*) as vowels FROM Players
  WHERE LEFT(Players.name, 1) IN ('A', 'E', 'I', 'O', 'U')
  GROUP BY Players.country_id
) as leader_first
JOIN (
  SELECT Players.country_id, count(*) as total FROM Players
  GROUP BY Players.country_id
) as leader_second ON leader_first.country_id = leader_second.country_id
ORDER BY 2 DESC LIMIT 1;
```

## Запрос 5
Для Олимпийских игр 2000 года найдите 5 стран с минимальным соотношением количества групповых медалей к численности населения.

```sql
SELECT country.country_id
FROM olympics
  JOIN events 
    ON events.olympic_id = olympics.olympic_id
  JOIN results 
    ON events.event_id = results.event_id
  JOIN players 
    ON p.player_id =results.player_id
  JOIN countries 
    ON countries.country_id = players.country_id
WHERE year = 2000
  AND is_team_event = 1
GROUP BY country.country_id, country.population
ORDER BY CAST(COUNT(results.medal) AS decimal) / country.population
LIMIT 5;
```