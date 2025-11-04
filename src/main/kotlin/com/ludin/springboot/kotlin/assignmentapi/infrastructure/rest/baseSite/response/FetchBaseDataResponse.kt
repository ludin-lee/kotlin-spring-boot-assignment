package com.ludin.springboot.kotlin.assignmentapi.infrastructure.rest.baseSite.response

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class FetchBaseDataResponse (
    @field:Schema(description = "현재 시간")
    val timestamp: LocalDateTime = LocalDateTime.now(),

    @field:Schema(description = "데이터")
    val data: String? = null,

    @field:Schema(description = "메세지", example = "성공")
    val message: String? = "성공",
 )