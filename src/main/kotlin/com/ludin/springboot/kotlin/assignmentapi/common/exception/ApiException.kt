package com.ludin.springboot.kotlin.assignmentapi.common.exception

import com.ludin.springboot.kotlin.assignmentapi.common.model.ApiCode


class ApiException(
    val apiCode: ApiCode,
    errorMessage: String? = null,
    cause: Throwable? = null,
    val data: Any? = null
) : RuntimeException(errorMessage ?: apiCode.message, cause)