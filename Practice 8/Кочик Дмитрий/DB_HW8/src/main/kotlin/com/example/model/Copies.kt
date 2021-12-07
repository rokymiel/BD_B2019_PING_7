package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

object Copies : Table() {
    val ISBN = text("ISBN").references(Books.ISBN)
    val copyNumber = text("copyNumber").uniqueIndex()
    val shelfPosition = integer("shelfPosition")
}

@Serializable
data class Copy(val ISBN: String, val copyNumber: String, val shelfPosition: Int)