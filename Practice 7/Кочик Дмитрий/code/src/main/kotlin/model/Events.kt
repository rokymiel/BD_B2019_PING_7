package model

import model.Constants.size100
import model.Constants.size20
import model.Constants.size40
import model.Constants.size7
import org.jetbrains.exposed.sql.Table

object Events : Table() {
    val eventId = char("event_id", size7).uniqueIndex("events_event_id_key")
    val name = char("name", size40)
    val eventType = char("eventtype", size20)
    val olympicId = char("olympic_id", size7).references(Olympics.olympicId)
    val isTeamEvent = integer("is_team_event").check("is_team_event") {
        it.inList(listOf(0, 1))
    }
    val numPlayersInTeam = integer("num_players_in_team")
    var resultNotedIn = char("result_noted_in", size100)
}