package com.example.service

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import javax.sql.DataSource

object DatabaseFactory {
    fun connect(){
        val pool = hikariDS()
        runFlyway(pool)
        Database.connect(pool)
    }

    private fun hikariDS(): HikariDataSource {
        val hikariConfig = HikariConfig("db.properties")
        return HikariDataSource(hikariConfig)
    }

    private fun runFlyway(datasource: DataSource) {
        val flyway = Flyway.configure()
            //.baselineOnMigrate(true)
            .dataSource(datasource)
            .load()
        //flyway.info()
        flyway.migrate()
    }
}