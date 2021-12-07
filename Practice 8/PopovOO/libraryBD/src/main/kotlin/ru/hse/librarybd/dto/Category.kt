package ru.hse.librarybd.dto

import javax.persistence.*

@Entity(name = "categories")
data class Category(
    @Id
    val name: String,
    @ManyToOne
    val parentCategory: Category? = null
) {
    constructor() : this("", null)
}
