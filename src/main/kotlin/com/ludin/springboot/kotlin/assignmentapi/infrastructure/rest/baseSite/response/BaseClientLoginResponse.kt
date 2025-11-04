package com.ludin.springboot.kotlin.assignmentapi.infrastructure.rest.baseSite.response

data class BaseClientLoginResponse (
    val token:String,
    val expires:Long,
)