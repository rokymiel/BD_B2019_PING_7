package com.example

import io.ktor.server.netty.*
import com.example.plugins.*
import com.example.service.DatabaseConnector
import com.example.service.DatabaseFactory
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import kotlinx.serialization.json.Json

fun main(args: Array<String>) {
    DatabaseFactory.connect()
    EngineMain.main(args)
}

fun Application.module() {
    val connector = DatabaseConnector()
    connector.initTables()
    install(ContentNegotiation) {
        json(Json { prettyPrint = true })
    }
    configureRouting(connector)
}
