package com.example.service

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.config.*
import org.jetbrains.exposed.sql.Database
import java.util.*

object DatabaseFactory {
    fun connect() {
        val pool = hikariDS()
        Database.connect(pool)
    }

    private fun hikariDS(): HikariDataSource {
        val appConfig = HoconApplicationConfig(ConfigFactory.load())
        val url = appConfig.property("dao.url").getString()
        val driver = appConfig.property("dao.driver").getString()
        val username = appConfig.property("dao.username").getString()
        val password = appConfig.property("dao.password").getString()
        val props = Properties()
        props.setProperty("driverClassName", driver)
        props.setProperty("jdbcUrl", url)
        props.setProperty("username", username)
        props.setProperty("password", password)
        val config = HikariConfig(props)
        return HikariDataSource(config)
    }
}