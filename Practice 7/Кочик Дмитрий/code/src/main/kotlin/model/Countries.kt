package model

import model.Constants.size4
import model.Constants.size40
import org.jetbrains.exposed.sql.Table

object Countries : Table() {
    val name = varchar("name", size40)
    val countryId = char("country_id", size4).uniqueIndex("countries_country_id_key")
    val areaSqKm = integer("area_sqkm")
    val population = integer("population")
}