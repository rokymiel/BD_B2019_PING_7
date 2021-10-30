
# Задание 6
### Для Олимпийских игр 2004 года сгенерируйте список (год рождения, количество игроков, количество золотых медалей), содержащий годы, в которые родились игроки, количество игроков, родившихся в каждый из этих лет, которые выиграли по крайней мере одну золотую медаль, и количество золотых медалей, завоеванных игроками, родившимися в этом году.
```sql
SELECT date_part('year', Players.birthdate) as birth_date,
       count(DISTINCT Players.player_id)    as players_number,
       count(Results.medal)                 as gold_medals_number
FROM Olympics
         JOIN Events ON Events.olympic_id = Olympics.olympic_id
         JOIN Results ON Events.event_id = Results.event_id
         JOIN Players ON Players.player_id = Results.player_id
WHERE Olympics.year = 2004
  AND Results.medal = 'GOLD'
GROUP BY birth_date
```
### Перечислите все индивидуальные (не групповые) соревнования, в которых была ничья в счете, и два или более игрока выиграли золотую медаль.
```sql
SELECT Events.event_id
FROM Events
         JOIN Results ON Events.event_id = Results.event_id
WHERE Events.is_team_event = 0
  AND Results.medal = 'GOLD'
GROUP BY Events.event_id
HAVING count(Results.medal) > 1
```
### Найдите всех игроков, которые выиграли хотя бы одну медаль (GOLD, SILVER и BRONZE) на одной Олимпиаде. (player-name, olympic-id).
```sql
SELECT DISTINCT Players.name, Events.olympic_id
FROM Players
         JOIN Results ON Results.player_id = Players.player_id
         JOIN Events ON Events.event_id = Results.event_id
```
### В какой стране был наибольший процент игроков (из перечисленных в наборе данных), чьи имена начинались с гласной?
```sql
SELECT t1.country_id
FROM (
         SELECT Players.country_id, count(*) as vowels_count
         FROM Players
         WHERE left(Players.name, 1) IN ('A', 'E', 'I', 'O', 'U')
         GROUP BY Players.country_id
     ) as t1
         JOIN (
    SELECT Players.country_id, count(*) as players_count_in_country
    FROM Players
    GROUP BY Players.country_id
) as t2 ON t1.country_id = t2.country_id
ORDER BY cast(vowels_count as decimal) / players_count_in_country DESC
LIMIT 1
```
### Для Олимпийских игр 2000 года найдите 5 стран с минимальным соотношением количества групповых медалей к численности населения.
```sql
SELECT Countries.country_id
FROM Olympics
         JOIN Events ON Events.olympic_id = Olympics.olympic_id
         JOIN Results ON Events.event_id = Results.event_id
         JOIN Players ON Players.player_id = Results.player_id
         JOIN Countries ON Countries.country_id = Players.country_id
WHERE Olympics.year = 2000
  AND Events.is_team_event = 1
GROUP BY Countries.country_id, Countries.population
ORDER BY cast(count(Results.medal) as decimal) / Countries.population
LIMIT 5
```
