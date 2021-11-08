import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

  public static void main(String[] args) throws Exception {
    Connection connection = DriverManager
        .getConnection("jdbc:postgresql://127.0.0.1:5432/", "", "");
    Statement st = connection.createStatement();
    st.execute("drop table if exists Results;\n"
        + "  drop table if exists Players;\n"
        + "  drop table if exists Events;\n"
        + "  drop table if exists Olympics;\n"
        + "  drop table if exists Countries;\n"
        + "  create table Countries (\n"
        + "    name char(40),\n"
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
        + "    name char(40),\n"
        + "    player_id char(10) unique,\n"
        + "    country_id char(3),\n"
        + "    birthdate date,\n"
        + "    foreign key (country_id) references Countries(country_id)\n"
        + "  );\n"
        + "  create table Events (\n"
        + "    event_id char(7) unique,\n"
        + "    name char(40),\n"
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

    Faker faker = new Faker(new Locale("en-US"));
    Random random = new Random();

    for (int i = 0; i < 100; i++) {
      st.execute("insert into Countries (name, area_sqkm, population) values("
          + faker.name().title() + ", "
          + faker.number().randomNumber(8, false) + ", "
          + faker.number().randomNumber(8, false)
          + ")");
    }

    for (int i = 0; i < 500; i++) {
      st.execute("insert into players(name, country_id, birthdate) values("
          + faker.name().fullName() + ", "
          + faker.number().randomNumber(2, true) + ", "
          + faker.date().birthday() + ", "
          + ")");
    }

    for (int i = 0; i < 20; i++) {
      int randomYear = random.nextInt() % 120 + 1920;
      Date startDate = new Date(randomYear, 1, random.nextInt() % 28 + 1);
      Date endDate = new Date(randomYear, random.nextInt() % 11 + 2,
          random.nextInt() % 28 + 1);
      st.execute(
          "insert into Olympics (city, year, startdate, enddate) values("
              + faker.name().title() + ", "
              + randomYear + ", "
              + startDate + ", "
              + endDate
              + ')');
    }
  }
}
