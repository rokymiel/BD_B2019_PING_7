package ru.hse.librarybd.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.hse.librarybd.dto.Book

@Repository
interface BookRepository : CrudRepository<Book, String>
