from argparse import ArgumentParser
from datetime import timedelta
import psycopg2 as psy
from faker import Faker

parser = ArgumentParser()
parser.add_argument("url")
parser.add_argument("--seed", type=int)
parser.add_argument("-d", help="draw probability", type=float, default=0.001)
parser.add_argument("-c", help="num of countries", type=int, default=100)
parser.add_argument("-p", help="num of players", type=int, default=1000)
parser.add_argument("-o", help="num of olympics", type=int, default=10)
parser.add_argument("-e", help="num of events", type=int, default=100)
args = parser.parse_args()

if args.seed != None:
    Faker.seed(args.seed)
faker = Faker()

random_choices = lambda arr: faker.random.choice(arr)
def generate_countries():
    for _ in range(args.c):
        id = faker.unique.country_code()
        name = faker.country()[:40]
        area_sqkm = faker.random_int(100, 999999)
        population = faker.random_int(10000, 999999999)

        cursor.execute("INSERT INTO Countries VALUES(%s, %s, %s, %s)", (name, id, area_sqkm, population))
        country_ids.append(id)


def generate_players():
    for _ in range(args.p):
        id = faker.unique.numerify("##-###-###")
        name = faker.name()[:40]
        birthday = faker.date_object()
        country = random_choices(country_ids)

        cursor.execute("INSERT INTO Players VALUES(%s, %s, %s, %s)", (name, id, country, birthday))
        player_ids.append(id)


def generate_olympics():
    for _ in range(args.o):
        country = random_choices(country_ids)
        city = faker.city()
        year = faker.unique.random_int(1900, 2020, 2)
        id = f"{city[:2].upper()}-{year}"

        start_date = faker.date_object().replace(year=year)
        end_date = start_date + timedelta(days=14)

        cursor.execute("INSERT INTO Olympics VALUES(%s, %s, %s, %s, %s, %s)", (id, country, city, year, start_date, end_date))
        olympic_ids.append(id)


def generate_events():
    for i in range(args.e):
        id = f"E{i}"
        type = random_choices(['SWI', 'ATH'])
        olympic = random_choices(olympic_ids)
        result_noted_in = random_choices(['meters', 'seconds', 'points'])

        name = ""
        if faker.boolean():
            name += random_choices(
                ["50m", "100m", "110m", "200m", "400m", "800m", "1500m", "3000m", "5000m", "10000m", "20km", "50km"])
            name += ' '
        if faker.boolean():
            name += random_choices(
                ["Decathlon", "Discus", "Hammer", "Heptathlon", "High", "Javelin", "Long", "Marathon", "Pole", "Shot",
                 "Triple"])
            name += ' '
        if faker.boolean():
            name += random_choices(
                ["Backstroke", "Breaststroke", "Butterfly", "Freestyle", "Hurdles", "Jump", "Put", "Throw", "Vault",
                 "Walk"])
            name += ' '
        name += random_choices(["Men", "Women"])

        cursor.execute("INSERT INTO Events VALUES(%s, %s, %s, %s, 0, -1, %s)", (id, name, type, olympic, result_noted_in))
        event_ids.append(id)


def generate_results():
    for event_id in event_ids:
        scores = sorted([faker.pyfloat(positive=True, max_value=5000, right_digits=3) for _ in range(3)])
        for score, medal in zip(scores, ["GOLD", "SILVER", "BRONZE"]):
            player = random_choices(player_ids)
            cursor.execute("INSERT INTO Results VALUES(%s, %s, %s, %s)", (event_id, player, medal, score))
            if faker.pyfloat(min_value=0, max_value=1) < args.d:
                second_player = random_choices(player_ids)
                while second_player == player:
                    second_player = random_choices(player_ids)
                cursor.execute("INSERT INTO Results VALUES(%s, %s, %s, %s)", (event_id, second_player, medal, score))

connection = psy.connect(args.url)
cursor = connection.cursor()
cursor.execute("""
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
 """)

country_ids = []
player_ids = []
olympic_ids = []
event_ids = []

generate_countries()
generate_players()
generate_olympics()
generate_events()
generate_results()

connection.commit()
cursor.close()
connection.close()
