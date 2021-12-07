package ru.hse.librarybd

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LibraryBdApplication

fun main(args: Array<String>) {
    runApplication<LibraryBdApplication>(*args)
}
