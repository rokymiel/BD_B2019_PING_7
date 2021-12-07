package model

import model.Constants.size10
import model.Constants.size7
import org.jetbrains.exposed.sql.Table

object Results : Table() {
    val eventId = char("event_id", size7).references(Events.eventId)
    val playerId = char("player_id", size10).references(Players.playerId)
    val medal = char("medal", size7)
    val result = float("result")
}