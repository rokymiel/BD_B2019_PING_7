package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

object Categories : Table() {
    val categoryName = text("categoryName").uniqueIndex()
    val parentCat = text("parentCat").references(categoryName).nullable()
}

@Serializable
data class Category(val categoryName: String, val parentCat: String?)