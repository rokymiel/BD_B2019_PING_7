package model

import model.Constants.size4
import model.Constants.size50
import model.Constants.size7
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.date
import org.jetbrains.exposed.sql.`java-time`.datetime

object Olympics : Table() {
    val olympicId = char("olympic_id", size7).uniqueIndex("olympics_olympic_id_key")
    val countryId = char("country_id", size4).references(Countries.countryId)
    val city = char("city", size50)
    val year = integer("year")
    val date = date("date").nullable()
    val endDate = date("enddate")
}