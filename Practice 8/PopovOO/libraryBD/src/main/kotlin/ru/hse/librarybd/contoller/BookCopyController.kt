package ru.hse.librarybd.contoller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.hse.librarybd.contoller.abstraction.EntityController
import ru.hse.librarybd.dto.BookCopy
import ru.hse.librarybd.dto.BookCopyDto
import ru.hse.librarybd.service.BookCopyService

@RestController
@RequestMapping("book/copy")
class BookCopyController(
    bookCopyService: BookCopyService
) : EntityController<BookCopy, BookCopyDto, Long>(bookCopyService)
