package ru.hse.librarybd.service

import org.springframework.stereotype.Service
import ru.hse.librarybd.dto.BookCopy
import ru.hse.librarybd.dto.BookCopyDto
import ru.hse.librarybd.repository.BookCopyRepository
import ru.hse.librarybd.repository.BookRepository
import ru.hse.librarybd.service.abstraction.EntityService

@Service
class BookCopyService(
    bookCopyRepository: BookCopyRepository,
    private val bookRepository: BookRepository
) : EntityService<BookCopy, BookCopyDto, Long>(
    bookCopyRepository,
    "Book copy"
) {

    override fun BookCopyDto.toEntity() = BookCopy(
        copyId,
        bookRepository.findById(bookISBN).orElseThrow {
            throwNotFoundException(bookISBN, "Book")
        },
        shelfNumber
    )
}