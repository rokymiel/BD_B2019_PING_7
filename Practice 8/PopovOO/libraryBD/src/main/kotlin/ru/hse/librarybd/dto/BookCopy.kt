package ru.hse.librarybd.dto

import ru.hse.librarybd.dto.abstraction.EntityDto
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity(name = "book_copies")
data class BookCopy(
    @Id @GeneratedValue
    val copyId: Long = -1,
    @ManyToOne
    val book: Book,
    val shelfNumber: Int
) {
    constructor() : this(-1, Book(), 0)
}

data class BookCopyDto(
    val copyId: Long = -1,
    val bookISBN: String,
    val shelfNumber: Int
): EntityDto<Long> {
    override fun getId() = copyId
}
