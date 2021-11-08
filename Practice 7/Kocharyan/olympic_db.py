from argparse import ArgumentParser
from datetime import timedelta
import psycopg2
from faker import Faker

# Start constants
URL = "postgresql://localhost/task7"
SEED = None
DRAW = 0.001
COUNTRIES_COUNT = 100
PLAYERS_COUNT = 1000
OLYMPICS_COUNT = 5
EVENTS_COUNT = 500

# Method constants
AREA_MIN = 555
AREA_MAX = 1234567
POPULATION_MIN = 1000000
POPULATION_MAX = 18000000
YEAR_MIN = 1980
YEAR_MAX = 2020
YEAR_GAP = 4
DAYS = 15
DRAW_MIN = 0
DRAW_MAX = 1

# Common constants
length = 1
max_value = 1000
right_digits = 3
positive = True
zero_index = 0

sql_command = """
  drop table if exists Results;
  drop table if exists Players;
  drop table if exists Events;
  drop table if exists Olympics;
  drop table if exists Countries;
  create table Countries (
    name char(40),
    country_id char(3) unique,
    area_sqkm integer,
    population integer
  );
  create table Olympics (
    olympic_id char(7) unique,
    country_id char(3),
    city char(50),
    year integer,
    startdate date,
    enddate date,
    foreign key (country_id) references Countries(country_id)
  );
  create table Players (
    name char(40),
    player_id char(10) unique,
    country_id char(3),
    birthdate date,
    foreign key (country_id) references Countries(country_id)
  );
  create table Events (
    event_id char(7) unique,
    name char(40),
    eventtype char(20),
    olympic_id char(7),
    is_team_event integer check (is_team_event in (0, 1)),
    num_players_in_team integer,
    result_noted_in char(100),
    foreign key (olympic_id) references Olympics(olympic_id)
  );
  create table Results (
    event_id char(7),
    player_id char(10),
    medal char(7),
    result float,
    foreign key (event_id) references Events(event_id),
    foreign key (player_id) references players(player_id)
  );
"""

if SEED != None:
    Faker.seed(SEED)
fake = Faker()


connection = psycopg2.connect(URL)
cursor = connection.cursor()
cursor.execute(sql_command)


def choice(arr):
    return fake.random_choices(arr, length=length)[zero_index]


def floats(n):
    return [fake.pyfloat(positive=positive, max_value=max_value, right_digits=right_digits) for _ in range(n)]


country_ids = []
for _ in range(COUNTRIES_COUNT):
    id = fake.unique.country_code('alpha-3')
    name = fake.country()
    while len(name) > 40:
        name = fake.country()
    area_sqkm = fake.random_int(AREA_MIN, AREA_MAX)
    population = fake.random_int(POPULATION_MIN, POPULATION_MAX)

    cursor.execute("insert into Countries values(%s, %s, %s, %s);", (name, id, area_sqkm, population))
    country_ids.append(id)


player_ids = []
for _ in range(PLAYERS_COUNT):
    name = fake.name()
    birthday = fake.date_object()
    country = choice(country_ids)

    names = [e for e in name.split(' ') if e.isalpha()]
    id = fake.unique.numerify(names[-1][:5] + names[0][:3] + '##').upper()

    cursor.execute("insert into Players values(%s, %s, %s, %s);", (name, id, country, birthday))
    player_ids.append(id)


olympic_ids = []
for _ in range(OLYMPICS_COUNT):
    country = choice(country_ids)
    city = fake.city()
    year = fake.unique.random_int(YEAR_MIN, YEAR_MAX, YEAR_GAP)
    id = city[:3].upper() + str(year)

    date = fake.date_object().replace(year=year)
    date2 = date + timedelta(days=DAYS)

    cursor.execute("insert into Olympics values(%s, %s, %s, %s, %s, %s);", (id, country, city, year, date, date2))
    olympic_ids.append(id)


event_ids = []
for i in range(EVENTS_COUNT):
    id = 'E' + str(i)
    type = choice(['SWI', 'ATH'])
    olympic = choice(olympic_ids)
    result_noted_in = choice(['meters', 'seconds', 'points'])

    name = ""
    if fake.boolean():
        name += choice(
            ["50m", "100m", "110m", "200m", "400m", "800m", "1500m", "3000m", "5000m", "10000m", "20km", "50km"])
        name += ' '
    if fake.boolean():
        name += choice(
            ["Decathlon", "Discus", "Hammer", "Heptathlon", "High", "Javelin", "Long", "Marathon", "Pole", "Shot",
             "Triple"])
        name += ' '
    if fake.boolean():
        name += choice(
            ["Backstroke", "Breaststroke", "Butterfly", "Freestyle", "Hurdles", "Jump", "Put", "Throw", "Vault",
             "Walk"])
        name += ' '
    name += choice(["Men", "Women"])

    cursor.execute("insert into Events values(%s, %s, %s, %s, 0, -1, %s);", (id, name, type, olympic, result_noted_in))
    event_ids.append(id)


for event_id in event_ids:
    scores = sorted(floats(3))
    for score, medal in zip(scores, ["GOLD", "SILVER", "BRONZE"]):
        player = choice(player_ids)
        cursor.execute("insert into Results values(%s, %s, %s, %s);", (event_id, player, medal, score))
        if fake.pyfloat(min_value=DRAW_MIN, max_value=DRAW_MAX) < DRAW:
            player = choice(player_ids)
            cursor.execute("insert into Results values(%s, %s, %s, %s);", (event_id, player, medal, score))


connection.commit()
cursor.close()
connection.close()