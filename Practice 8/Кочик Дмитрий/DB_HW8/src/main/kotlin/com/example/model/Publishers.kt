package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

object Publishers : Table() {
    val pubName = text("pubName").uniqueIndex()
    val pubAddress = text("pubAddress")
}

@Serializable
data class Publisher(val pubName: String, val pubAddress: String)