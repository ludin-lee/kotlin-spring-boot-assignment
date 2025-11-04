package com.ludin.springboot.kotlin.assignmentapi.config

import org.springframework.boot.context.properties.ConfigurationProperties

//TODO - 인증서버에서 환경변수 받도록
@ConfigurationProperties(prefix = "security")
data class SecurityProperties (
    val baseClientUrl :String = "http://localhost:8080",
    val baseClientSecret:String = "secret",
)