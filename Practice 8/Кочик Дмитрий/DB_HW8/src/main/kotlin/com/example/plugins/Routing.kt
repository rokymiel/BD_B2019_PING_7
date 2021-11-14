package com.example.plugins

import com.example.model.*
import com.example.service.DatabaseConnector
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*

fun Application.configureRouting(connector: DatabaseConnector) {
    routing {
        dbRouting(connector)
    }
}

fun Route.dbRouting(connector: DatabaseConnector) {
    route("/db") {
        get("/fill/{size}") {
            val size = call.parameters["size"]?.toIntOrNull()
            if (size == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            connector.fillAllTables(size)
            call.respond(HttpStatusCode.Created)
        }

        get("/{table}") {
            val params = call.request.queryParameters
            val tableName = call.parameters["table"] ?: throw IllegalStateException("Must provide table name")
            when (tableName) {
                Books.tableName -> call.respond(HttpStatusCode.OK, connector.getBooks(params))
                Copies.tableName -> call.respond(HttpStatusCode.OK, connector.getBookCopies(params))
                Borrowings.tableName -> call.respond(HttpStatusCode.OK, connector.getBorrowings(params))

                Categories.tableName -> call.respond(HttpStatusCode.OK, connector.getCategories())
                BookCategories.tableName -> call.respond(HttpStatusCode.OK, connector.getBookCategories())
                Publishers.tableName -> call.respond(HttpStatusCode.OK, connector.getPublishers())
                Readers.tableName -> call.respond(HttpStatusCode.OK, connector.getReaders())
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
                Books.tableName -> connector.addBook(call.receive())
                Copies.tableName -> connector.addBookCopy(call.receive())
                Borrowings.tableName -> connector.addBorrowing(call.receive())
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
                Books.tableName -> connector.updateBook(call.receive())
                Copies.tableName -> connector.updateBookCopy(call.receive())
                Borrowings.tableName -> connector.updateBorrowing(call.receive())
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
                Books.tableName -> connector.deleteBook(params)
                Copies.tableName -> connector.deleteBookCopy(params)
                Borrowings.tableName -> connector.deleteBorrowing(params)
                else -> {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }
            }
            call.respond(HttpStatusCode.OK)
        }
    }
}
