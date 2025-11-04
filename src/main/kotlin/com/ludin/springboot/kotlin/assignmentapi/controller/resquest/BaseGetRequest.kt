package com.ludin.springboot.kotlin.assignmentapi.controller.resquest

import io.swagger.v3.oas.annotations.media.Schema


data class BaseGetRequest(
    @field:Schema(description = "데이터 키값", required = true, example = "ludin")
    val key: String,
)