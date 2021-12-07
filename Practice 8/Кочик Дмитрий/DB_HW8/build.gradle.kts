val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.serialization") version "1.5.31"
    id("com.jetbrains.exposed.gradle.plugin") version "0.2.1"
    id("org.flywaydb.flyway") version "8.0.4"
}

group = "com.example"
version = "0.0.1"
application {
    mainClass.set("com.example.ApplicationKt")
}

repositories {
    mavenCentral()
}

//flyway {
//    url = "jdbc:postgresql://localhost:5432/postgres"
//    baselineOnMigrate = true
//    locations = arrayOf("classpath:db/migration")
//    user = "postgres"
//    password = "123"
//}

//exposedCodeGeneratorConfig {
//    val dbProperties = loadProperties("${projectDir}/db.properties")
//    configFilename = "exposedConf.yml"
//    user = dbProperties["dataSource.user"].toString()
//    password = dbProperties["dataSource.password"].toString()
//    databaseName = dbProperties["dataSource.database"].toString()
//    databaseDriver = dbProperties["dataSource.driver"].toString()
//}

//sourceSets.main {
//    java.srcDirs("build/tables")
//}
//
//tasks.generateExposedCode {
//    dependsOn("clean")
//}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-serialization:$ktor_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    implementation("org.jetbrains.exposed:spring-transaction:0.36.1")
    implementation("org.postgresql:postgresql:42.1.4")
    implementation("org.jetbrains.exposed:exposed-java-time:0.30.1")
    implementation("org.jetbrains.exposed:exposed-core:0.35.2")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.35.2")

    implementation("com.zaxxer:HikariCP:5.0.0")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.2.0")

    implementation("org.flywaydb:flyway-core:7.10.0")
}