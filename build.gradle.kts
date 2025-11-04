import org.gradle.kotlin.dsl.named
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName


plugins {
    kotlin("jvm") version "2.2.10"
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("plugin.spring") version "2.2.0"
    id("org.flywaydb.flyway") version "11.9.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"


val openApiVersion = "2.8.5" // 2.8.6 ~ 2.8.9 속도 저하 이슈 있음
val ktorVersion = "3.2.1"
val flywayVersion = "11.9.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${openApiVersion}")
    implementation("io.ktor:ktor-client-core:${ktorVersion}")
    implementation("io.ktor:ktor-client-cio:${ktorVersion}")
    implementation("io.ktor:ktor-client-logging:${ktorVersion}")
    implementation("io.ktor:ktor-client-content-negotiation:${ktorVersion}")
    implementation("io.ktor:ktor-client-apache:${ktorVersion}")
    implementation("io.ktor:ktor-serialization-jackson:${ktorVersion}")

    runtimeOnly("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.flywaydb:flyway-core:$flywayVersion")
    implementation("org.flywaydb:flyway-database-postgresql:$flywayVersion")
    implementation("org.postgresql:postgresql:42.7.7")

}


repositories {
    mavenCentral()
}


buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.testcontainers:postgresql:1.20.3")
        classpath("org.testcontainers:testcontainers:1.20.3")
        classpath("org.flywaydb:flyway-database-postgresql:11.9.1")
    }
}

abstract class DatabaseService : BuildService<BuildServiceParameters.None>, AutoCloseable {
    val container = PostgreSQLContainer(
        DockerImageName.parse("public.ecr.aws/docker/library/postgres:16-alpine")
            .asCompatibleSubstituteFor("postgres")
    ).apply {
        withDatabaseName("ludin")
        start()
    }

    override fun close() {
        if (container.isRunning) {
            println("Stopping DB")
            container.stop()
        }
    }
}


val databaseService = project.gradle.sharedServices.registerIfAbsent("databaseService", DatabaseService::class.java) { }

flyway {
    val container = databaseService.get().container
    url = container.jdbcUrl
    user = container.username
    password = container.password
    cleanDisabled = false
}



kotlin {
    jvmToolchain(21)
}

//임시 시작용 - 로컬 환경 스타트
tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
    args = listOf("--spring.profiles.active=local")

    usesService(databaseService)
    doFirst {
        val container = databaseService.get().container
        systemProperty("spring.datasource.url", container.jdbcUrl)
        systemProperty("spring.datasource.username", container.username)
        systemProperty("spring.datasource.password", container.password)
    }
}

tasks.named("flywayMigrate") {
    dependsOn(tasks.named("flywayClean"))
    usesService(databaseService)
}
tasks {
    bootJar {
        archiveVersion.set("")
    }
    jar {
        enabled = false
    }
}

tasks.named<BootBuildImage>("bootBuildImage") {
    builder.set("paketobuildpacks/builder-jammy-base:latest")
}