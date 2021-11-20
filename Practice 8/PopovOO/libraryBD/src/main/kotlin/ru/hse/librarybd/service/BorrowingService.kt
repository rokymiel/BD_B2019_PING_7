package ru.hse.librarybd.service

import org.springframework.stereotype.Service
import ru.hse.librarybd.dto.Borrowing
import ru.hse.librarybd.dto.BorrowingDto
import ru.hse.librarybd.repository.BorrowingRepository
import ru.hse.librarybd.repository.ReaderRepository
import ru.hse.librarybd.service.abstraction.EntityService

@Service
class BorrowingService(
    borrowingRepository: BorrowingRepository,
    private val bookCopyService: BookCopyService,
    private val readerRepository: ReaderRepository,
) : EntityService<Borrowing, BorrowingDto, Long>(
    borrowingRepository,
    "Borrowing"
) {

    override fun BorrowingDto.toEntity() =
        Borrowing(
            id,
            bookCopyService.find(bookCopyId),
            readerRepository.findById(readerId).orElseThrow {
                throwNotFoundException(readerId, "Reader")
            },
            isReturned
        )

    fun returnBorrowing(id: Long) =
        find(id).apply {
            if (isReturned)
                throw IllegalArgumentException("This borrowing is already returned")
            isReturned = true
        }
}
