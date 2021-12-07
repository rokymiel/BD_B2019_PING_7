package ru.hse.librarybd.dto

import java.sql.Date
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity(name = "readers")
data class Reader(
    @Id @GeneratedValue
    val readerId: Long = -1,
    val lastName: String,
    val firstName: String,
    val address: String,
    val dateOfBirth: Date,
    @OneToMany
    val borrowings: List<Borrowing> = mutableListOf()
) {
    constructor() : this(-1, "", "", "", Date(0), mutableListOf())
}
