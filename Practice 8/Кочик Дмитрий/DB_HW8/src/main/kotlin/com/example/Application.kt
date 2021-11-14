package com.example

import com.example.model.Readers
import io.ktor.server.netty.*
import com.example.plugins.*
import com.example.service.DatabaseAccessor
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
    val accessor = DatabaseAccessor()
    accessor.initTables()
    install(ContentNegotiation) {
        json(Json { prettyPrint = true })
    }
    configureRouting(accessor)
}
