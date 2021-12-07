### 1.
```sql
select extract(year from birthdate) as year,  
       count(distinct players.name) as players,  
       count(*)                     as gold_medals_total  
from players  
         inner join results r on players.player_id = r.player_id  
         inner join events e on r.event_id = e.event_id  
         inner join olympics o on e.olympic_id = o.olympic_id  
where o.year = 2004  
  and r.medal = 'GOLD'  
group by extract(year from birthdate)  
```
### 2.
```sql
select r.event_id
from events
         inner join results r on events.event_id = r.event_id
where events.is_team_event = 0
  and r.medal = 'GOLD'
group by r.event_id
having count(*) > 1
```
### 3.
```sql
select distinct players.player_id, name
from players
         inner join results r on players.player_id = r.player_id
order by name
```
### 4.
```sql
select country_id, round(100 * (count(case when name ~* '^[aeiou]' then 1 end))::numeric / count(*), 2) as percentage
from players
group by country_id
order by percentage desc limit 1
```
### 5.
```sql
select c.country_id
from olympics
         inner join events e on olympics.olympic_id = e.olympic_id
         inner join results r on e.event_id = r.event_id
         inner join players p on r.player_id = p.player_id
         inner join countries c on c.country_id = p.country_id
where year = 2000
  and e.is_team_event = 1
group by c.country_id, population
order by count (distinct e.event_id):: numeric / population
    limit 5
```
