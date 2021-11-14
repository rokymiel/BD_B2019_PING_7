package com.example.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.date

object Readers : Table() {
    val id = integer("id").uniqueIndex().autoIncrement()
    val lastName = text("lastName").uniqueIndex()
    val firstName = text("firstName")
    val address = text("address")
    val birthdate = date("date")
}

@Serializable
data class Reader(val id: Int, val lastName: String, val firstName: String, val address: String, val birthdate: LocalDate)