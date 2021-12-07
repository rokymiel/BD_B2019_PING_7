import static java.lang.Math.abs;

import com.github.javafaker.Faker;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Main {

  private static final Faker faker = new Faker(new Locale("en-US"));
  private static final Random random = new Random();

  private static final int COUNTRIES_COUNT = 100;
  private static final int PLAYERS_COUNT = 500;
  private static final int OLYMPICS_COUNT = 20;
  private static final int EVENTS_COUNT = 1000;

  public static void main(String[] args) throws Exception {

    Connection connection = DriverManager
        .getConnection("jdbc:postgresql://localhost:5430/postgres");
    Statement st = connection.createStatement();
    st.execute("drop table if exists Results;\n"
        + "  drop table if exists Players;\n"
        + "  drop table if exists Events;\n"
        + "  drop table if exists Olympics;\n"
        + "  drop table if exists Countries;\n"
        + "  create table Countries (\n"
        + "    name char(100),\n"
        + "    country_id char(3) unique,\n"
        + "    area_sqkm integer,\n"
        + "    population integer\n"
        + "  );\n"
        + "  create table Olympics (\n"
        + "    olympic_id char(7) unique,\n"
        + "    country_id char(3),\n"
        + "    city char(50),\n"
        + "    year integer,\n"
        + "    startdate date,\n"
        + "    enddate date,\n"
        + "    foreign key (country_id) references Countries(country_id)\n"
        + "  );\n"
        + "  create table Players (\n"
        + "    name char(100),\n"
        + "    player_id char(10) unique,\n"
        + "    country_id char(3),\n"
        + "    birthdate date,\n"
        + "    foreign key (country_id) references Countries(country_id)\n"
        + "  );\n"
        + "  create table Events (\n"
        + "    event_id char(7) unique,\n"
        + "    name char(100),\n"
        + "    eventtype char(20),\n"
        + "    olympic_id char(7),\n"
        + "    is_team_event integer check (is_team_event in (0, 1)),\n"
        + "    num_players_in_team integer,\n"
        + "    result_noted_in char(100),\n"
        + "    foreign key (olympic_id) references Olympics(olympic_id)\n"
        + "  );\n"
        + "  create table Results (\n"
        + "    event_id char(7),\n"
        + "    player_id char(10),\n"
        + "    medal char(7),\n"
        + "    result float,\n"
        + "    foreign key (event_id) references Events(event_id),\n"
        + "    foreign key (player_id) references players(player_id)\n"
        + "  );");

    for (int i = 1; i <= COUNTRIES_COUNT; i++) {
      st.execute(
          "insert into Countries (name, country_id, area_sqkm, population) values("
              + "'" + faker.name().title() + "', "
              + i + ", "
              + faker.number().randomNumber(8, false) + ", "
              + faker.number().randomNumber(8, false)
              + ")");
    }

    for (int i = 1; i <= PLAYERS_COUNT; i++) {
      st.execute(
          "insert into players(name, player_id, country_id, birthdate) values("
              + '\'' + getRandomName() + "', "
              + i + ", "
              + faker.number().randomNumber(2, true) + ", "
              + "'" + faker.date().birthday() + "'"
              + ")");
    }

    for (int i = 1; i <= OLYMPICS_COUNT; i++) {
      int randomYear = random.nextInt(120) + 1920;
      Date startDate = new Date(randomYear, 1, random.nextInt() % 28 + 1);
      Date endDate = new Date(randomYear, random.nextInt() % 11 + 2,
          random.nextInt(28) + 1);
      st.execute(
          "insert into Olympics (olympic_id, country_id, city, year, startdate, enddate) values("
              + i + ", "
              + "'" + (random.nextInt(COUNTRIES_COUNT) + 1) + "', "
              + "'" + faker.name().title() + "', "
              + randomYear + ", "
              + "'" + startDate + "', "
              + "'" + endDate + "'"
              + ')');
    }

    for (int i = 1; i <= EVENTS_COUNT; i++) {
      boolean isTeamEvent = random.nextBoolean();
      int numOfPlayersInTeam = isTeamEvent ? random.nextInt(20) + 2 : 1;
      st.execute(
          "insert into Events (event_id, name, eventtype, olympic_id, is_team_event, num_players_in_team, result_noted_in) values("
              + i + ", "
              + "'" + faker.name().title() + "', "
              + "'" + faker.number().randomNumber() + ' ' + faker.name()
              .suffix() + "', "
              + (random.nextInt(OLYMPICS_COUNT) + 1) + ", "
              + (isTeamEvent ? 1 : 0) + ", "
              + numOfPlayersInTeam + ", "
              + "'m')");
    }

    for (int i = 1; i <= EVENTS_COUNT; i++) {
      int[] winners = getWinners(PLAYERS_COUNT);
      String[] medals = {"GOLD", "SILVER", "BRONZE"};
      for (int winnerI = 0; winnerI < 3; winnerI++) {
        st.execute(
            "insert into Results (event_id, player_id, medal, result) values("
                + i + ", "
                + winners[winnerI] + ", "
                + "'" + medals[winnerI] + "', "
                + random.nextFloat()
                + ")");
      }
    }
  }

  private static String getRandomName() {
    while (true) {
      String name = faker.name().name();
      if (!name.contains("'")) {
        return name;
      }
    }
  }

  private static int[] getWinners(int maxWinnerId) {
    while (true) {
      int[] winners = random.ints(3).map((int a) -> abs(a) % maxWinnerId + 1)
          .toArray();
      if (winners.length == 3) {
        return winners;
      }
    }
  }

}
