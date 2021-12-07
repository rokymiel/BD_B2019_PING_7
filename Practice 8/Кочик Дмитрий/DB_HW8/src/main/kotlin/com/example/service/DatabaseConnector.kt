package com.example.service

import com.example.model.*
import io.ktor.http.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random

class DatabaseConnector {
    private val textLength = 20
    private val charPool = "qwertyuiopasdfghjklzxcvbnm"
    private val tables = listOf(BookCategories, Books, Borrowings, Categories, Copies, Publishers, Readers)

    fun initTables() {
        transaction {
            SchemaUtils.create(
                BookCategories,
                Books,
                Borrowings,
                Categories,
                Copies,
                Publishers,
                Readers
            )
        }
    }

    fun getTable(name: String) = tables.firstOrNull { it.tableName == name }

    fun fillAllTables(size: Int) {
        fillPublishers(size)
        fillBooks(size)
        fillCopies(size)
        fillReaders(size)
        fillCategories(size)
        fillBookCategories(size)
        fillBorrowings(size)
    }

    fun deleteBook(params: Parameters) {
        val id = params[Books.ISBN.name] ?: return
        transaction {
            Books.deleteWhere { Books.ISBN eq id }
        }
    }

    fun deleteBookCopy(params: Parameters) {
        val id = params[Copies.copyNumber.name] ?: return
        transaction {
            Copies.deleteWhere { Copies.copyNumber eq id }
        }
    }

    fun deleteBorrowing(params: Parameters) {
        val readerId = params[Borrowings.readerNr.name]?.toIntOrNull() ?: return
        val copyNumb = params[Borrowings.copyNumber.name] ?: return
        transaction {
            Borrowings.deleteWhere { (Borrowings.copyNumber eq copyNumb).and(Borrowings.readerNr eq readerId) }
        }
    }

    fun updateBook(book: Book) {
        transaction {
            Books.update({ Books.ISBN eq book.ISBN }) {
                it[title] = book.title
                it[author] = book.author
                it[pagesNum] = book.pagesNum
                it[pubYear] = book.pubYear
                it[pubName] = book.pubName
            }
        }
    }

    fun updateBookCopy(copy: Copy) {
        transaction {
            Copies.update({ Copies.copyNumber eq copy.copyNumber }) {
                it[ISBN] = copy.ISBN
                it[shelfPosition] = copy.shelfPosition
            }
        }
    }

    fun updateBorrowing(borrowing: Borrowing) {
        transaction {
            Borrowings.update({ (Borrowings.copyNumber eq borrowing.copyNumber).and(Borrowings.readerNr eq borrowing.readerNr) }) {
                it[ISBN] = borrowing.ISBN
                it[returnDate] = LocalDate.parse(borrowing.returnDate.toString())
            }
        }
    }

    fun addBook(book: Book) {
        transaction {
            Books.insert {
                it[ISBN] = book.ISBN
                it[title] = book.title
                it[author] = book.author
                it[pagesNum] = book.pagesNum
                it[pubYear] = book.pubYear
                it[pubName] = book.pubName
            }
        }
    }

    fun addBookCopy(copy: Copy) {
        transaction {
            Copies.insert {
                it[ISBN] = copy.ISBN
                it[copyNumber] = copy.copyNumber
                it[shelfPosition] = copy.shelfPosition
            }
        }
    }

    fun addBorrowing(borrowing: Borrowing) {
        transaction {
            Borrowings.insert {
                it[ISBN] = borrowing.ISBN
                it[readerNr] = borrowing.readerNr
                it[copyNumber] = borrowing.copyNumber
                it[returnDate] = LocalDate.parse(borrowing.returnDate.toString())
            }
        }
    }

    fun getBooks(params: Parameters = Parameters.Empty): List<Book> {
        if (params.isEmpty()) {
            return transaction { Books.selectAll().map { toBook(it) } }
        }
        return transaction { Books.selectAll().map { toBook(it) } }.filter {
            (it.ISBN == (params[Books.ISBN.name] ?: it.ISBN)) &&
                    (it.title == (params[Books.title.name] ?: it.title)) &&
                    (it.author == (params[Books.author.name] ?: it.author)) &&
                    (it.pagesNum.toString() == (params[Books.pagesNum.name] ?: it.pagesNum.toString())) &&
                    (it.pubYear.toString() == (params[Books.pubYear.name] ?: it.pubYear.toString())) &&
                    (it.pubName == (params[Books.pubName.name] ?: it.pubName))
        }
    }

    fun getBookCopies(params: Parameters = Parameters.Empty): List<Copy> {
        if (params.isEmpty()) {
            return transaction { Copies.selectAll().map { toCopy(it) } }
        }
        return transaction { Copies.selectAll().map { toCopy(it) } }.filter {
            (it.ISBN == (params[Copies.ISBN.name] ?: it.ISBN)) &&
                    (it.copyNumber == (params[Copies.copyNumber.name] ?: it.copyNumber)) &&
                    (it.shelfPosition.toString() == (params[Copies.shelfPosition.name]
                        ?: it.shelfPosition.toString()))
        }
    }

    fun getBorrowings(params: Parameters = Parameters.Empty): List<Borrowing> {
        if (params.isEmpty()) {
            return transaction { Borrowings.selectAll().map { toBorrowing(it) } }
        }
        return transaction { Borrowings.selectAll().map { toBorrowing(it) } }.filter {
            (it.ISBN == (params[Borrowings.ISBN.name] ?: it.ISBN)) &&
                    (it.readerNr.toString() == (params[Borrowings.readerNr.name] ?: it.readerNr.toString())) &&
                    (it.copyNumber == (params[Borrowings.copyNumber.name] ?: it.copyNumber)) &&
                    (it.returnDate.toString() == (params[Borrowings.returnDate.name] ?: it.returnDate.toString()))
        }
    }

    fun getPublishers(): List<Publisher> = transaction { Publishers.selectAll().map { toPublisher(it) } }
    fun getCategories(): List<Category> = transaction { Categories.selectAll().map { toCategory(it) } }
    fun getReaders(): List<Reader> = transaction { Readers.selectAll().map { toReader(it) } }
    fun getBookCategories(): List<BookCategory> =
        transaction { BookCategories.selectAll().map { toBookCategory(it) } }

    private fun toBook(row: ResultRow): Book = Book(
        ISBN = row[Books.ISBN],
        title = row[Books.title],
        author = row[Books.author],
        pagesNum = row[Books.pagesNum],
        pubYear = row[Books.pubYear],
        pubName = row[Books.pubName]
    )

    private fun toCopy(row: ResultRow): Copy = Copy(
        ISBN = row[Copies.ISBN],
        copyNumber = row[Copies.copyNumber],
        shelfPosition = row[Copies.shelfPosition]
    )

    private fun toPublisher(row: ResultRow): Publisher = Publisher(
        pubName = row[Publishers.pubName],
        pubAddress = row[Publishers.pubAddress]
    )

    private fun toCategory(row: ResultRow): Category = Category(
        categoryName = row[Categories.categoryName],
        parentCat = row[Categories.parentCat]
    )

    private fun toReader(row: ResultRow): Reader = Reader(
        id = row[Readers.id],
        lastName = row[Readers.lastName],
        firstName = row[Readers.firstName],
        address = row[Readers.address],
        birthdate = kotlinx.datetime.LocalDate.parse(row[Readers.birthdate].toString())
    )

    private fun toBookCategory(row: ResultRow): BookCategory = BookCategory(
        ISBN = row[BookCategories.ISBN],
        categoryName = row[BookCategories.categoryName]
    )

    private fun toBorrowing(row: ResultRow): Borrowing = Borrowing(
        readerNr = row[Borrowings.readerNr],
        ISBN = row[Borrowings.ISBN],
        copyNumber = row[Borrowings.copyNumber],
        returnDate = kotlinx.datetime.LocalDate.parse(row[Borrowings.returnDate].toString())
    )

    private fun fillPublishers(size: Int) {
        for (x in 0 until size) {
            transaction {
                Publishers.insert {
                    it[pubName] =
                        getValid { str: String -> Publishers.select { pubName.eq(str) }.empty() }
                    it[pubAddress] = randomStringWithLength()
                }
            }
        }
    }

    private fun fillBooks(size: Int) {
        val pubNames = getPublishers().map { it.pubName }
        for (x in 0 until size) {
            transaction {
                Books.insert {
                    it[ISBN] =
                        getValid { str: String -> Books.select { ISBN.eq(str) }.empty() }
                    it[title] = randomStringWithLength()
                    it[author] = randomStringWithLength()
                    it[pagesNum] = Random.nextInt(10, 2000)
                    it[pubYear] = Random.nextInt(1500, 2022)
                    it[pubName] = pubNames.random()
                }
            }
        }
    }

    private fun fillCopies(size: Int) {
        val bookNames = getBooks().map { it.ISBN }
        for (x in 0 until size) {
            transaction {
                Copies.insert {
                    it[ISBN] = bookNames.random()
                    it[copyNumber] =
                        getValid { str: String -> Copies.select { copyNumber.eq(str) }.empty() }
                    it[shelfPosition] = Random.nextInt(Int.MAX_VALUE)
                }
            }
        }
    }

    private fun fillReaders(size: Int) {
        for (x in 0 until size) {
            transaction {
                Readers.insert {
                    it[lastName] = randomStringWithLength()
                    it[firstName] = randomStringWithLength()
                    it[address] = randomStringWithLength()
                    it[birthdate] = getDate()
                }
            }
        }
    }

    private fun fillCategories(size: Int) {
        val names = mutableListOf<String>()
        for (x in 0 until size / 2) {
            transaction {
                val name = (Categories.insert {
                    it[categoryName] =
                        getValid { str: String -> Categories.select { categoryName.eq(str) }.empty() }
                } get Categories.categoryName)
                names.add(name)
            }
        }
        for (x in 0 until size / 2) {
            transaction {
                Categories.insert {
                    it[categoryName] =
                        getValid { str: String -> Categories.select { categoryName.eq(str) }.empty() }
                    it[parentCat] = names.random()
                }
            }
        }
    }

    private fun fillBookCategories(size: Int) {
        val bookNames = getBooks().map { it.ISBN }
        val catNames = getCategories().map { it.categoryName }
        for (x in 0 until size) {
            transaction {
                BookCategories.insert {
                    it[ISBN] = bookNames.random()
                    it[categoryName] = catNames.random()
                }
            }
        }
    }

    private fun fillBorrowings(size: Int) {
        val copies = getBookCopies()
        val readers = getReaders().map { it.id }
        for (x in 0 until size) {
            transaction {
                Borrowings.insert {
                    val copy = copies.random()
                    it[readerNr] = readers.random()
                    it[ISBN] = copy.ISBN
                    it[copyNumber] = copy.copyNumber
                    it[returnDate] = getDate()
                }
            }
        }
    }

    private fun randomStringWithLength(length: Int = textLength) = ((1..length)
        .map { Random.nextInt(0, charPool.length) }
        .map(charPool::get)
        .joinToString(""))

    private fun getValid(length: Int = textLength, checker: (String) -> Boolean): String {
        while (true) {
            val newStr = randomStringWithLength(length)
            val unique = transaction {
                checker(newStr)
            }
            if (unique) {
                return newStr
            }
        }
    }

    private fun getDate(): LocalDate {
        val minDay = LocalDate.of(1900, 1, 1).toEpochDay()
        val maxDay = LocalDate.of(2021, 12, 31).toEpochDay()
        val randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay)
        return LocalDate.ofEpochDay(randomDay)
    }
}