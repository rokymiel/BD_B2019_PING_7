# Задание №6. Борисов Костя БПИ197
## Запрос 1
Для Олимпийских игр 2004 года сгенерируйте список (год рождения, количество игроков, количество золотых медалей),
содержащий годы, в которые родились игроки, количество игроков, родившихся в каждый из этих лет,
которые выиграли по крайней мере одну золотую медаль, и количество золотых медалей, завоеванных игроками, родившимися в этом году.

```sql
SELECT
  date_part('year', Players.birthdate) as byear,
  count(DISTINCT Players.player_id),
  count(Results.medal)
FROM Olympics
JOIN Events ON Events.olympic_id = Olympics.olympic_id
JOIN Results ON Events.event_id = Results.event_id
JOIN Players ON Players.player_id = Results.player_id
WHERE year = 2004 AND Results.medal = 'GOLD'
GROUP BY 1;
```

## Запрос 2
Перечислите все индивидуальные (не групповые) соревнования, в которых была ничья в счете, и два или более игрока выиграли золотую медаль.

```sql
SELECT Events.event_id FROM Events
JOIN Results ON Events.event_id = Results.event_id
WHERE is_team_event = 0 AND Results.medal = 'GOLD'
GROUP BY Events.event_id HAVING count(Results.medal) >= 2;
```

## Запрос 3
Найдите всех игроков, которые выиграли хотя бы одну медаль (GOLD, SILVER и BRONZE) на одной Олимпиаде. (player-name, olympic-id).

```sql
SELECT DISTINCT Players.name, Events.olympic_id FROM Players
JOIN Results ON Results.player_id = Players.player_id
JOIN Events ON Events.event_id = Results.event_id
WHERE Results.medal IN ('GOLD', 'SILVER', 'BRONZE');
```

## Запрос 4
В какой стране был наибольший процент игроков (из перечисленных в наборе данных), чьи имена начинались с гласной?

```sql
SELECT t1.country_id, cast(vowels as decimal)/total FROM (
  SELECT Players.country_id, count(*) as vowels FROM Players
  WHERE left(Players.name, 1) IN ('A', 'E', 'I', 'O', 'U')
  GROUP BY Players.country_id
) as t1
JOIN (
  SELECT Players.country_id, count(*) as total FROM Players
  GROUP BY Players.country_id
) as t2 ON t1.country_id = t2.country_id
ORDER BY 2 DESC LIMIT 1;
```

## Запрос 5
Для Олимпийских игр 2000 года найдите 5 стран с минимальным соотношением количества групповых медалей к численности населения.

```sql
SELECT Countries.country_id, cast(count(Results.medal) as decimal)/population FROM Olympics
JOIN Events ON Events.olympic_id = Olympics.olympic_id
JOIN Results ON Events.event_id = Results.event_id
JOIN Players ON Players.player_id = Results.player_id
JOIN Countries ON Countries.country_id = Players.country_id
WHERE year = 2000 AND is_team_event = 1
GROUP BY Countries.country_id, population
ORDER BY 2 LIMIT 5;
```
