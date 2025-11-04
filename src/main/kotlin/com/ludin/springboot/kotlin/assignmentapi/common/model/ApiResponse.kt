package com.ludin.springboot.kotlin.assignmentapi.common.model

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse<T>(
    @field:Schema(description = "현재 시간")
    val timestamp: LocalDateTime = LocalDateTime.now(),

    @field:Schema(description = "데이터")
    val data: T? = null,

    @field:Schema(description = "메세지", example = "성공")
    val message: String? = "성공",
)