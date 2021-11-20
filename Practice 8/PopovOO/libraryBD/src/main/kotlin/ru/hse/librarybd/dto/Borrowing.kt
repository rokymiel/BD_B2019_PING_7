package ru.hse.librarybd.dto

import ru.hse.librarybd.dto.abstraction.EntityDto
import java.sql.Date
import java.time.Duration
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity(name = "borrowings")
data class Borrowing(
    @Id @GeneratedValue
    val id: Long = -1,
    @ManyToOne
    val bookCopy: BookCopy,
    @ManyToOne
    val reader: Reader,
    var isReturned: Boolean = false
) {
    constructor() : this(-1, BookCopy(), Reader())

    val returnDate: Date = Date(
        System.currentTimeMillis() +
                (if (isReturned) Duration.ofDays(90).toMillis() else 0L)
    )
}

data class BorrowingDto(
    val id: Long = -1,
    val bookCopyId: Long,
    val readerId: Long,
    var isReturned: Boolean = false
): EntityDto<Long> {
    override fun getId() = id
}
