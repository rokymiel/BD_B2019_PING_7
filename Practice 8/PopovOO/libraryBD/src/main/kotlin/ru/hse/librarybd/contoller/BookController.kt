package ru.hse.librarybd.contoller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.hse.librarybd.contoller.abstraction.EntityController
import ru.hse.librarybd.dto.Book
import ru.hse.librarybd.dto.BookDto
import ru.hse.librarybd.service.BookService

@RestController
@RequestMapping("book")
class BookController(
    bookService: BookService
) : EntityController<Book, BookDto, String>(bookService)
