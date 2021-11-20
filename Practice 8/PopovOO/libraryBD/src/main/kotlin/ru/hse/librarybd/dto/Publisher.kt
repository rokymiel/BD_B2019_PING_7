package ru.hse.librarybd.dto

import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "publishers")
data class Publisher(
    @Id
    val name: String,
    val address: String
) {
    constructor() : this("", "")
}
