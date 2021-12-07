package ru.hse.librarybd.contoller

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.hse.librarybd.contoller.abstraction.EntityController
import ru.hse.librarybd.dto.Borrowing
import ru.hse.librarybd.dto.BorrowingDto
import ru.hse.librarybd.service.BorrowingService

@RestController
@RequestMapping("borrowing")
class BorrowingController(
    private val borrowingService: BorrowingService
) : EntityController<Borrowing, BorrowingDto, Long>(borrowingService) {

    @PostMapping("{id}/return")
    fun returnBook(@PathVariable id: Long) =
        borrowingService.returnBorrowing(id)
}
