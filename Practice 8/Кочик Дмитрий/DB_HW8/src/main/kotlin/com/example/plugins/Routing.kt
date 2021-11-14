package com.example.plugins

import com.example.model.*
import com.example.service.DatabaseAccessor
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*

fun Application.configureRouting(accessor: DatabaseAccessor) {
    routing {
        dbRouting(accessor)
    }
}

fun Route.dbRouting(accessor: DatabaseAccessor) {
    route("/db") {
        get("/fill/{size}") {
            val size = call.parameters["size"]?.toIntOrNull()
            if (size == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            accessor.fillAllTables(size)
            call.respond(HttpStatusCode.Created)
        }

        get("/{table}") {
            val params = call.request.queryParameters
            val tableName = call.parameters["table"] ?: throw IllegalStateException("Must provide table name")
            when (tableName) {
                Books.tableName -> call.respond(HttpStatusCode.OK, accessor.getBooks(params))
                Copies.tableName -> call.respond(HttpStatusCode.OK, accessor.getBookCopies(params))
                Borrowings.tableName -> call.respond(HttpStatusCode.OK, accessor.getBorrowings(params))

                Categories.tableName -> call.respond(HttpStatusCode.OK, accessor.getCategories())
                BookCategories.tableName -> call.respond(HttpStatusCode.OK, accessor.getBookCategories())
                Publishers.tableName -> call.respond(HttpStatusCode.OK, accessor.getPublishers())
                Readers.tableName -> call.respond(HttpStatusCode.OK, accessor.getReaders())
                else -> call.respond(HttpStatusCode.BadRequest)
            }
        }

        post("/{table}") {
            val tableName = call.parameters["table"]
            if (tableName == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            when (tableName) {
                Books.tableName -> accessor.addBook(call.receive())
                Copies.tableName -> accessor.addBookCopy(call.receive())
                Borrowings.tableName -> accessor.addBorrowing(call.receive())
                else -> {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }
            }
            call.respond(HttpStatusCode.Created)
        }

        put("/{table}") {
            val tableName = call.parameters["table"]
            if (tableName == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }
            when (tableName) {
                Books.tableName -> accessor.updateBook(call.receive())
                Copies.tableName -> accessor.updateBookCopy(call.receive())
                Borrowings.tableName -> accessor.updateBorrowing(call.receive())
                else -> {
                    call.respond(HttpStatusCode.BadRequest)
                    return@put
                }
            }
            call.respond(HttpStatusCode.OK)
        }

        delete("/{table}") {
            val params = call.request.queryParameters
            val tableName = call.parameters["table"]
            if (tableName == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            when (tableName) {
                Books.tableName -> accessor.deleteBook(params)
                Copies.tableName -> accessor.deleteBookCopy(params)
                Borrowings.tableName -> accessor.deleteBorrowing(params)
                else -> {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }
            }
            call.respond(HttpStatusCode.OK)
        }
    }
}
