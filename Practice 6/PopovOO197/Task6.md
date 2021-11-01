### task 1
select p.birthdate, COUNT(distinct p.player_id), COUNT(1) from players p, events e, results r 
where e.olympic_id = 'ATH2004' and e.event_id=r.event_id and r.player_id=p.player_id and r.medal='GOLD'
group by p.birthdate

### task 2
select e.event_id from events e, results r
where e.is_team_event=0 and e.event_id=r.event_id and r.medal='GOLD'
group by e.event_id having COUNT(*)>1

// Я решил не сравнивать результаты золотых медалистов вручную. Т.е. я считаю: несколько участников получили золотую медаль => у них был одинаковый результат

### task 3
select distinct p.name, o.olympic_id from players p, olympics o, events e, results r
where o.olympic_id=e.olympic_id and e.event_id=r.event_id and r.player_id=p.player_id

// Во-первых, я не проверял значение result.medal, т.к. оно не может быть не из {GOLD, SILVER, BRONZE}.
// Во-вторых, не понял из задания, нужно ли брать уникальных участников (а что, если он взял медаль на нескольких олимпиадах? мне возвращать только 1ю олимпиаду? или как?). Поэтому я возвращаю все такие уникальные пары (участник, олимпиада)

### task 4
select c.country_id from countries c, players p
WHERE p.name ~* '^[aeiou]' and c.country_id=p.country_id
group by c.country_id order by COUNT(*)/(select COUNT(*) from players p1 WHERE c.country_id=p1.country_id ) 
desc limit 1

### task 5
select c.country_id from countries c, olympics o, players p, events e, results r
where p.country_id=c.country_id and r.player_id=p.player_id and r.event_id=e.event_id
and o.year=2000 and e.is_team_event=1
group by c.country_id, c.population
order by (COUNT(*)*1.0)/c.population 
limit 5