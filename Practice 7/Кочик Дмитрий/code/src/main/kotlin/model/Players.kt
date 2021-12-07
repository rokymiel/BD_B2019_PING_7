package model

import model.Constants.size10
import model.Constants.size4
import model.Constants.size40
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.date

object Players : Table() {
    val name = char("name", size40)
    val playerId = char("player_id", size10).uniqueIndex("players_player_id_key")
    val countryId = char("country_id", size4).references(Countries.countryId)
    val birthdate = date("birthdate")
}