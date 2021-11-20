package ru.hse.librarybd.service

import org.springframework.stereotype.Service
import ru.hse.librarybd.dto.Book
import ru.hse.librarybd.dto.BookDto
import ru.hse.librarybd.repository.BookRepository
import ru.hse.librarybd.repository.CategoryRepository
import ru.hse.librarybd.repository.PublisherRepository
import ru.hse.librarybd.service.abstraction.EntityService

@Service
class BookService(
    bookRepository: BookRepository,
    private val publisherRepository: PublisherRepository,
    private val categoryRepository: CategoryRepository
) : EntityService<Book, BookDto, String>(bookRepository, "Book") {

    override fun BookDto.toEntity(): Book {
        val categories = categoryNames.map {
            categoryRepository.findById(it).orElseThrow {
                throwNotFoundException(it, "Category")
            }
        }
        return Book(
            ISBN,
            year,
            title,
            author,
            numberOfPages,
            publisherRepository.findById(publisherName).get(),
            categories
        )
    }
}