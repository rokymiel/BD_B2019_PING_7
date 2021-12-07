# Задание 6: Агроскин Александр, БПИ197

# №1: 

Для Олимпийских игр 2004 года сгенерируйте список (год рождения, количество игроков, количество золотых медалей), содержащий годы, в которые родились игроки, количество игроков, родившихся в каждый из этих лет, которые выиграли по крайней мере одну золотую медаль, и количество золотых медалей, завоеванных игроками, родившимися в этом году.

```sql
SELECT EXTRACT(YEAR FROM p.birthdate), COUNT(DISTINCT p.player_id), COUNT(r.medal)
FROM players p
         JOIN results r ON p.player_id = r.player_id
         JOIN events e ON r.event_id = e.event_id
         JOIN olympics o ON e.olympic_id = o.olympic_id
WHERE r.medal = 'GOLD'
  AND o.year = 2004
GROUP BY EXTRACT(YEAR FROM p.birthdate)
```

# №2:

Перечислите все индивидуальные (не групповые) соревнования, в которых была ничья в счете, и два или более игрока выиграли золотую медаль.

```sql
SELECT e.event_id
FROM Events e
         JOIN results r ON e.event_id = r.event_id
WHERE is_team_event = 0
  AND r.medal = 'GOLD'
GROUP BY e.event_id
HAVING COUNT(r.medal) > 1
```

# №3:

Найдите всех игроков, которые выиграли хотя бы одну медаль (GOLD, SILVER и BRONZE) на одной Олимпиаде. (player-name, olympic-id).

```sql
SELECT DISTINCT p.name, e.olympic_id
FROM players p
         JOIN results r ON p.player_id = r.player_id
         JOIN events e ON e.event_id = r.event_id
```

# №4:

В какой стране был наибольший процент игроков (из перечисленных в наборе данных), чьи имена начинались с гласной?

```sql
SELECT vow.country_id
FROM (
         SELECT p.country_id, COUNT(DISTINCT p) AS num
         FROM players p
         WHERE LEFT(p.name, 1) IN ('A', 'a', 'E', 'e', 'I', 'i', 'O', 'o', 'U', 'u')
         GROUP BY p.country_id
     ) AS vow
         JOIN (
    SELECT p.country_id, COUNT(DISTINCT p) AS denom
    FROM players p
    GROUP BY p.country_id
) AS all_players ON vow.country_id = all_players.country_id
ORDER BY CAST(vow.num AS decimal) / all_players.denom DESC
LIMIT 1
```

# №5:

Для Олимпийских игр 2000 года найдите 5 стран с минимальным соотношением количества групповых медалей к численности населения.

```sql
SELECT c.country_id
FROM olympics o
         JOIN events e ON e.olympic_id = o.olympic_id
         JOIN results r ON e.event_id = r.event_id
         JOIN players p ON p.player_id = r.player_id
         JOIN countries c ON c.country_id = p.country_id
WHERE year = 2000
  AND is_team_event = 1
GROUP BY c.country_id, c.population
ORDER BY CAST(COUNT(r.medal) AS decimal) / c.population
LIMIT 5
```
