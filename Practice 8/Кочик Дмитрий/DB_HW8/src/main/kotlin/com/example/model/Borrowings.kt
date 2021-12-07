package com.example.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.date

object Borrowings : Table() {
    val readerNr = integer("readerNr").references(Readers.id)
    val ISBN = text("ISBN").references(Books.ISBN)
    val copyNumber = text("copyNumber").references(Copies.copyNumber)
    val returnDate = date("returnDAte")
}

@Serializable
data class Borrowing(
    val readerNr: Int,
    val ISBN: String,
    val copyNumber: String,
    val returnDate: LocalDate
)