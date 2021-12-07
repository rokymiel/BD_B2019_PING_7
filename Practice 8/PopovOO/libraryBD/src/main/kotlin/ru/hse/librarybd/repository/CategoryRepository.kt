package ru.hse.librarybd.repository

import org.springframework.data.repository.CrudRepository
import ru.hse.librarybd.dto.Book
import ru.hse.librarybd.dto.BookCopy
import ru.hse.librarybd.dto.Borrowing
import ru.hse.librarybd.dto.Category

interface CategoryRepository :CrudRepository<Category, String>