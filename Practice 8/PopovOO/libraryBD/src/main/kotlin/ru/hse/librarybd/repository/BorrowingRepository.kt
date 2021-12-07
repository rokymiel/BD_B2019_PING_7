package ru.hse.librarybd.repository

import org.springframework.data.repository.CrudRepository
import ru.hse.librarybd.dto.Borrowing

interface BorrowingRepository : CrudRepository<Borrowing, Long>