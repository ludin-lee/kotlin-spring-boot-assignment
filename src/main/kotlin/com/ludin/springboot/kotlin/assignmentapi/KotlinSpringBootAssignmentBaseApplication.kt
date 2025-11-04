package com.ludin.springboot.kotlin.assignmentapi

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.runApplication
import com.ludin.springboot.kotlin.assignmentapi.config.SecurityProperties
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties


@OpenAPIDefinition(servers = [Server(url = "/", description = "Default Server URL")])
@SpringBootApplication
@EnableConfigurationProperties(SecurityProperties::class)
class KotlinSpringBootAssignmentBaseApplication : CommandLineRunner {

    override fun run(vararg args: String?) {
        println("Ludin Server is running")
    }
}
fun main(args: Array<String>) {
    runApplication<KotlinSpringBootAssignmentBaseApplication>(*args)
}