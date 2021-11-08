import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
    application
}

group = "me.user"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")

    implementation("org.jetbrains.exposed:spring-transaction:0.36.1")
    implementation("org.postgresql:postgresql:42.1.4")
    implementation("org.jetbrains.exposed:exposed-java-time:0.30.1")
    implementation("org.slf4j:slf4j-api:1.7.29")
    implementation("org.slf4j:slf4j-simple:1.7.29")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

//tasks.withType<Jar> {
//    manifest {
//        attributes["Main-Class"] = "MainKt"
//    }
//}
//
//tasks.register<JavaExec>("runWithJavaExec") {
//    description = "Run the main class with JavaExecTask"
//    main = "MainKt"
//    classpath = sourceSets["main"].runtimeClasspath
//}

//val fatJar = task("fatJar", type = Jar::class) {
//    baseName = "${project.name}-fat"
//    manifest {
//        attributes["Implementation-Title"] = "Gradle Jar File Example"
//        attributes["Implementation-Version"] = version
//        attributes["Main-Class"] = "main"
//    }
//    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
//    with(tasks.jar.get() as CopySpec)
//}
//
//tasks {
//    "build" {
//        dependsOn(fatJar)
//    }
//}