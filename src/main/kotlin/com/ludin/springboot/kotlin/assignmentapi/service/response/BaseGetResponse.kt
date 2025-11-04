package com.ludin.springboot.kotlin.assignmentapi.service.response

import io.swagger.v3.oas.annotations.media.Schema

data class BaseGetResponse(
    @field:Schema(description = "데이터 값", required = true, example = "Hello World!")
    val value: String,
)