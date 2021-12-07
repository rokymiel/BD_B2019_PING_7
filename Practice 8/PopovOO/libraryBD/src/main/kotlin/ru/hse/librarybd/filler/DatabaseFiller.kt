package ru.hse.librarybd.filler

import com.github.javafaker.Faker
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.hse.librarybd.dto.*
import ru.hse.librarybd.repository.*
import java.util.*
import javax.annotation.PostConstruct

@Service
class DatabaseFiller(
    private val bookRepository: BookRepository,
    private val publisherRepository: PublisherRepository,
    private val readerRepository: ReaderRepository,
    private val categoryRepository: CategoryRepository,
    private val borrowingRepository: BorrowingRepository,
    private val bookCopyRepository: BookCopyRepository,
    @Value("\${faker.book.count}") private val bookCount: Int,
    @Value("\${faker.category.count}") private val categoryCount: Int,
    @Value("\${faker.publisher.count}") private val publisherCount: Int,
    @Value("\${faker.reader.count}") private val readerCount: Int,
    @Value("\${faker.bookcopy.count}") private val bookCopyCount: Int,
    @Value("\${faker.borrowing.count}") private val borrowingCount: Int
) {
    private val faker: Faker = Faker(Locale("en-US"))
    private val random = Random()

    @PostConstruct
    fun fillDatabase() {
        repeat(readerCount) {
            readerRepository.save(
                Reader(
                    -1,
                    faker.name().lastName(),
                    faker.name().firstName(),
                    faker.address().fullAddress(),
                    java.sql.Date(faker.date().birthday().time)
                )
            )
        }
        repeat(publisherCount) {
            publisherRepository.save(
                Publisher(
                    faker.book().publisher(),
                    faker.address().fullAddress()
                )
            )
        }
        val categories = mutableListOf<Category>()
        repeat(categoryCount) {
            categories.add(
                categoryRepository.save(
                    Category(
                        faker.name().title(),
                        if (faker.random()
                                .nextBoolean() && categories.size > 0
                        ) categories[faker.random()
                            .nextInt(categories.size)] else null
                    )
                )
            )
        }
        val publishers = publisherRepository.findAll().toList()
        repeat(bookCount) {
            val bookCategories = mutableListOf<Category>()
            repeat(random.nextInt(5)) {
                val category = categories[random.nextInt(categories.size)]
                if (bookCategories.find { c -> c.name == category.name } == null)
                    bookCategories.add(categories[random.nextInt(categories.size)])
            }
            bookRepository.save(
                Book(
                    faker.regexify("\\d{3}-\\d-\\d{4}-\\d{4}-\\d"),
                    faker.date().birthday().year,
                    faker.book().title(),
                    faker.book().author(),
                    faker.random().nextInt(1, 1000),
                    publishers[random.nextInt(publishers.size)],
                    bookCategories
                )
            )
        }
        val books = bookRepository.findAll().toList()
        repeat(bookCopyCount) {
            bookCopyRepository.save(
                BookCopy(
                    -1,
                    books[faker.random().nextInt(books.size)],
                    faker.random().nextInt(1, 100)
                )
            )
        }
        val bookCopies = bookCopyRepository.findAll().toList()
        val readers = readerRepository.findAll().toList()
        repeat(borrowingCount) {
            borrowingRepository.save(
                Borrowing(
                    -1,
                    bookCopies[faker.random().nextInt(bookCopies.size)],
                    readers[faker.random().nextInt(readers.size)],
                    faker.random().nextBoolean()
                )
            )
        }

        publisherRepository.save(Publisher("kek", "kek's adr"))
        categoryRepository.save(Category("kek", null))
    }
}