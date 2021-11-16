package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

object Books : Table() {
    val ISBN = text("ISBN").uniqueIndex()
    val title = text("title")
    val author = text("author")
    val pagesNum = integer("pagesNumb")
    val pubYear = integer("pubYear")
    val pubName = text("pubName").references(Publishers.pubName)
}

@Serializable
data class Book(
    val ISBN: String,
    val title: String,
    val author: String,
    val pagesNum: Int,
    val pubYear: Int,
    val pubName: String
)