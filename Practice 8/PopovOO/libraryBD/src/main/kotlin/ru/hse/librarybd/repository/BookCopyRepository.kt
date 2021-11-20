package ru.hse.librarybd.repository

import org.springframework.data.repository.CrudRepository
import ru.hse.librarybd.dto.Book
import ru.hse.librarybd.dto.BookCopy

interface BookCopyRepository :CrudRepository<BookCopy, Long>