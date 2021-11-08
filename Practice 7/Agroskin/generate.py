import datetime
import string
from argparse import ArgumentParser

import psycopg2
from faker import Faker

import queries

arg_parser = ArgumentParser()
arg_parser.add_argument("host", help="Host of the database")
arg_parser.add_argument("db", help="Name of the database")
arg_parser.add_argument("user", help="Database user login")
arg_parser.add_argument("pass", help="Database user password")
arg_parser.add_argument("-c", "--countries", type=int, default=100, help="Number of countries to insert")
arg_parser.add_argument("-o", "--olympics", type=int, default=1, help="Number of olympics to insert")
arg_parser.add_argument("-p", "--players", type=int, default=100, help="Number of players to insert")
arg_parser.add_argument("-e", "--events", type=int, default=100, help="Number of events to insert")

args = arg_parser.parse_args()
faker = Faker()

with psycopg2.connect(dbname=args.db, user=args.user,
                      password=args["pass"], host=args.host) as connection, connection.cursor() as curs:
    # Initializing
    curs.execute(queries.initialize)

    # Generating countries
    countries = [(faker.country(),
                  ''.join(faker.random.choices(string.ascii_uppercase, k=3)),
                  faker.random.randint(10000, 100000),
                  faker.random.randint(1000000, 10000000)) for _ in range(args.countries)]
    [curs.execute(queries.insert_country, args) for args in countries]

    # Generating olympics
    olympics = []
    for i in range(args.olympics):
        country = faker.random.choice(countries)[1]
        start = faker.date_object()
        year = start.year
        olympics.append((i, country, faker.city(), year, start, start + datetime.timedelta(days=30)))
    [curs.execute(queries.insert_olympic, args) for args in olympics]

    # Generating players
    players = [(faker.name(), i, faker.random.choice(countries)[1], faker.date()) for i in args.players()]
    [curs.execute(queries.insert_player, args) for args in players]

    # Generating events
    events = []
    for i in range(args.events):
        if faker.boolean():
            team = 1
            team_size = faker.random.randint(2, 5)
        else:
            team = 0
            team_size = -1
        events.append((i, faker.word(), ''.join(faker.random.choices(string.ascii_uppercase, k=3)),
                       faker.random.choice(olympics)[0], team, team_size, faker.word()))
    [curs.execute(queries.insert_event, args) for args in events]

    # Generating results
    results = []
    for event in events:
        res = sorted([faker.random.random() * 100 for _ in range(3)], reverse=True)
        for score, medal in zip(res, ["GOLD", "SILVER", "BRONZE"]):
            results.append((event[0], faker.unique.random.choice(players)[1], medal, score))
            while faker.boolean(chance_of_getting_true=15):
                results.append((event[0], faker.unique.random.choice(players)[1], medal, score))
        faker.unique.clear()
    [curs.execute(queries.insert_result, args) for args in results]
