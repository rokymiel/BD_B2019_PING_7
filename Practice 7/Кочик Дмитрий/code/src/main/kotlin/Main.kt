import model.*
import model.Countries.countryId
import model.Events.eventId
import model.Olympics.olympicId
import model.Players.playerId
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random.Default.nextFloat
import kotlin.random.Random.Default.nextInt

private val TABLES = listOf("Countries", "Events", "Olympics", "Players", "Results")
private const val CHAR_POOL = "qwertyuiopasdfghjklzxcvbnm"

fun rndSize(position: Int): Int {
    val size = nextInt(150)
    println("Random number for table ${TABLES[position]}: $size")
    return size
}

fun getDate(): LocalDate {
    val minDay = LocalDate.of(1900, 1, 1).toEpochDay()
    val maxDay = LocalDate.of(2021, 12, 31).toEpochDay()
    val randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay)
    return LocalDate.ofEpochDay(randomDay)
}

fun randomStringWithLength(length: Int) = ((1..length)
    .map { nextInt(0, CHAR_POOL.length) }
    .map(CHAR_POOL::get)
    .joinToString("")).capitalize()

fun getValid(length: Int, checker: (String) -> Boolean, converter: (String) -> String = { it }): Pair<String, String> {
    while (true) {
        val newStr = randomStringWithLength(length)
        val transformResult = converter(newStr)
        if (transformResult.isNotEmpty() && checker(transformResult)) {
            return newStr to transformResult
        }
    }
}

fun main(args: Array<String>) {
    if (args.size < 2) {
        println("Username and password are missed")
        return
    }

    Database.connect(
        "jdbc:postgresql://localhost:5432/postgres",
        "org.postgresql.Driver",
        args[0],
        args[1]
    )

    val countriesNumb = if (args.size >= 7) args[6].toIntOrNull() ?: rndSize(0) else rndSize(0)
    val eventsNumb = if (args.size >= 6) args[5].toIntOrNull() ?: rndSize(1) else rndSize(1)
    val olympicsNumb = if (args.size >= 5) args[4].toIntOrNull() ?: rndSize(2) else rndSize(2)
    val playersNumb = if (args.size >= 4) args[3].toIntOrNull() ?: rndSize(3) else rndSize(3)
    val resultsNumb = if (args.size >= 3) args[2].toIntOrNull() ?: rndSize(4) else rndSize(4)

    transaction {
        SchemaUtils.createMissingTablesAndColumns(Countries, Olympics, Players, Events, Results)
    }

    fillCountries(countriesNumb)
    fillEvents(eventsNumb)
    fillOlympics(olympicsNumb)
    fillPlayers(playersNumb)
    fillResults(resultsNumb)

    println("Done")
}

fun fillCountries(numb: Int) {
    transaction {
        val checker = { str: String -> Countries.select { countryId.eq(str) }.empty() }
        for (x in 0 until numb) {
            Countries.insert {
                val names = getValid(Constants.size40, checker) { str ->
                    str.toLowerCase().toCharArray().filter { letter -> letter !in "aeiou" }.take(3).joinToString("")
                        .toUpperCase()
                }
                it[name] = names.first
                it[countryId] = names.second
                it[areaSqKm] = nextInt(Int.MAX_VALUE)
                it[population] = nextInt(Int.MAX_VALUE)
            }
        }
    }
}

fun fillOlympics(numb: Int) {
    val checker = { str: String -> Olympics.select { olympicId.eq(str) }.empty() }
    val countries = transaction {
        Countries.selectAll()
    }
    for (x in 0 until numb) {
        transaction {
            Olympics.insert {
                val newName = getValid(Constants.size4, checker)
                val newId = countries.map { row -> row[Countries.countryId] }[nextInt(
                    countries.count().let { length -> if (length < Int.MAX_VALUE) length.toInt() else Int.MAX_VALUE })]
                val newYear = getDate()
                it[olympicId] = newName.first
                it[countryId] = newId
                it[city] = randomStringWithLength(Constants.size50)
                it[year] = newYear.year
                it[date] = newYear
                it[endDate] = LocalDate.ofEpochDay(newYear.toEpochDay() + nextInt(40))
            }
        }
    }
}

fun fillPlayers(numb: Int) {
    transaction {
        val olympicsSize = Olympics.selectAll().count().let { if (it < Int.MAX_VALUE) it.toInt() else Int.MAX_VALUE }
        val checker = { str: String -> Players.select { playerId.eq(str) }.empty() }
        for (x in 0 until numb) {
            Players.insert {
                val newName = getValid(Constants.size40, checker)
                val newNameId = getValid(Constants.size10, checker)
                val olympics = Olympics.selectAll().toList()[nextInt(olympicsSize)]
                val date = if (olympics[Olympics.date] != null) olympics[Olympics.date] else LocalDate.of(1901, 1, 1)
                it[name] = newName.first
                it[playerId] = newNameId.first
                it[countryId] = olympics[Olympics.countryId]
                it[birthdate] = LocalDate.ofEpochDay(
                    ThreadLocalRandom.current()
                        .nextLong(LocalDate.of(1900, 1, 1).toEpochDay(), date!!.toEpochDay())
                )
            }
        }
    }
}

fun fillEvents(numb: Int) {
    transaction {
        val olympicsSize = Olympics.selectAll().count().let { if (it < Int.MAX_VALUE) it.toInt() else Int.MAX_VALUE }
        val checker = { str: String -> Events.select { eventId.eq(str) }.empty() }
        for (x in 0 until numb) {
            Events.insert {
                val newId = getValid(Constants.size7, checker)
                val newName = getValid(Constants.size40, checker)
                val newType = getValid(Constants.size20, checker)
                val olympic = Olympics.selectAll().limit(nextInt(olympicsSize) + 1).last()[Olympics.olympicId]
                val isTeam = nextInt(2)
                val resultIn = nextInt(2)
                it[eventId] = newId.first
                it[name] = newName.first
                it[eventType] = newType.first
                it[olympicId] = olympic
                it[isTeamEvent] = isTeam
                it[numPlayersInTeam] = if (isTeam == 0) -1 else nextInt(12)
                it[resultNotedIn] = if (resultIn == 0) "meters" else "seconds"
            }
        }
    }
}

fun fillResults(numb: Int) {
    transaction {
        val res = listOf("GOLD", "SILVER", "BRONZE")
        val eventsSize = Events.selectAll().count().let { if (it < Int.MAX_VALUE) it.toInt() else Int.MAX_VALUE }
        val playersSize = Players.selectAll().count().let { if (it < Int.MAX_VALUE) it.toInt() else Int.MAX_VALUE }
        for (x in 0 until numb) {
            Results.insert {
                val event = Events.selectAll().limit(nextInt(eventsSize) + 1).last()[Events.eventId]
                val player = Players.selectAll().limit(nextInt(playersSize) + 1).last()[Players.playerId]
                it[eventId] = event
                it[playerId] = player
                it[medal] = res[nextInt(3)]
                it[result] = nextFloat() + nextInt(100)
            }
        }
    }
}