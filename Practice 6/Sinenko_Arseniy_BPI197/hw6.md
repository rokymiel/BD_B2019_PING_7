
# Задача 1
- Для Олимпийских игр 2004 года сгенерируйте список (год рождения, количество игроков, количество золотых медалей), содержащий годы, в которые родились игроки, количество игроков, родившихся в каждый из этих лет, которые выиграли по крайней мере одну золотую медаль, и количество золотых медалей, завоеванных игроками, родившимися в этом году.

```sql
select year_of_birth, PLAYERS_COUNT, gold_count
from (select count(*) as gold_count, extract(year from DATE(p.birthdate)) as year_of_birth
    from results
    inner join players p on results.player_id = p.player_id
    where medal = 'GOLD' and event_id in (
        select event_id
        from events
        where olympic_id in (
            select olympic_id
            from olympics
            where year = 2004)
    )
    group by extract(year from DATE(p.birthdate))) GOLD_COUNT
inner join (select extract(year from DATE(birthdate)) as y_o_b, count(*) as PLAYERS_COUNT
    from players
    where player_id in (
        select player_id
        from results
        where medal = 'GOLD' and event_id in (
            select event_id
            from events
            where olympic_id in (
                select olympic_id
                from olympics
                where year = 2004)
        )
    )
    group by extract(year from DATE(birthdate))) KEK on y_o_b = year_of_birth
```
# Задача 2
-   Перечислите все индивидуальные (не групповые) соревнования, в которых была ничья в счете, и два или более игрока выиграли золотую медаль.
```sql
select event_id, name  
from events  
where is_team_event = 0 and event_id in (  
    select event_id  
  from results  
    where medal = 'GOLD'  
  group by event_id, result  
  having count(*) >= 2  
  )
```
# Задача 3
-   Найдите всех игроков, которые выиграли хотя бы одну медаль (GOLD, SILVER и BRONZE) на одной Олимпиаде. (player-name, olympic-id).
```sql
select players.name, olympic_id  
from players  
inner join results r on players.player_id = r.player_id  
inner join events e on e.event_id = r.event_id  
where players.player_id in (  
	select player_id  
	from results  
	group by player_id)
```
# Задача 4
-   В какой стране был наибольший процент игроков (из перечисленных в наборе данных), чьи имена начинались с гласной?
```sql
select country_id  
from players  
where players.name ~ '^[aeiouAEIOU]'  
group by country_id  
order by count(*) DESC  
fetch first 1 rows only
```
# Задача 5
-   Для Олимпийских игр 2000 года найдите 5 стран с минимальным соотношением количества групповых медалей к численности населения.
```sql
select c.country_id  
from countries c  
inner join olympics o on c.country_id = o.country_id  
  and o.year = 2000  
inner join events e on o.olympic_id = e.olympic_id  
inner join results r on e.event_id = r.event_id  
where is_team_event = 1  
group by c.country_id, c.population  
order by count(*) / c.population  
fetch first 5 rows only
```
Синенко Арсений БПИ197
