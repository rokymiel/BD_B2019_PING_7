package ru.hse.librarybd.dto

import ru.hse.librarybd.dto.abstraction.EntityDto
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne


@Entity(name = "books")
data class Book(
    @Id
    val ISBN: String,
    val year: Int = 0,
    val title: String = "",
    val author: String = "",
    val numberOfPages: Int = 0,
    @ManyToOne
    val publisher: Publisher,
    @ManyToMany
    val categories: List<Category>
) {
    constructor() : this(
        "",
        0,
        "",
        "",
        0,
        Publisher("", ""),
        mutableListOf<Category>()
    )
}

data class BookDto(
    val ISBN: String,
    val year: Int = 0,
    val title: String = "",
    val author: String = "",
    val numberOfPages: Int = 0,
    val publisherName: String,
    val categoryNames: List<String>
) : EntityDto<String> {
    override fun getId() = ISBN
}
