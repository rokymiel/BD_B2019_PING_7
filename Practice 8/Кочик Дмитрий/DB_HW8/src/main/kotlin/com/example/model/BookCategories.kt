package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

object BookCategories : Table() {
    val ISBN = text("ISBN").references(Books.ISBN)
    val categoryName = text("categoryName").references(Categories.categoryName)
}

@Serializable
data class BookCategory(val ISBN: String, val categoryName: String)