# Задание 7: Агроскин Александр, БПИ197

Для запуска нужно иметь установленный `Python>=3.6` с библиотеками `Faker` и `psycopg2`. На вход команде необходимо дать хост, имя, логин и пароль к базе данных, а также по желанию количество сгенерированных объектов каждого типа: страны, олимпиады, игроки, события. Результаты генерируются автоматически, на каждое событие должны быть три медали с возможностью ничьи.

Значения по умолчанию:

* countries: 100
* olympics: 1
* players: 100
* events: 100

```bash
usage: generate.py [-h] [-c COUNTRIES] [-o OLYMPICS] [-p PLAYERS] [-e EVENTS] host db user pass

positional arguments:
  host                  Host of the database
  db                    Name of the database
  user                  Database user login
  pass                  Database user password

optional arguments:
  -h, --help            show this help message and exit
  -c COUNTRIES, --countries COUNTRIES
                        Number of countries to insert
  -o OLYMPICS, --olympics OLYMPICS
                        Number of olympics to insert
  -p PLAYERS, --players PLAYERS
                        Number of players to insert
  -e EVENTS, --events EVENTS
                        Number of events to insert
 ```
