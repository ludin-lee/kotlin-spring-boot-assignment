package com.ludin.springboot.kotlin.assignmentapi.common.model

import org.springframework.http.HttpStatus

enum class ApiCode(
    val httpStatus: HttpStatus,
    val code: Int,
    val message: String,
) {
    SUCCESS(HttpStatus.OK, 0, "성공"),


    // 1000 ~ Validation
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 1000, "잘못된 요청입니다."),

    // 2000 ~ NotFound
    NOT_FOUND(HttpStatus.NOT_FOUND, 2000, "데이터를 찾을 수 없습니다."),

    // 4000 ~ Auth Error
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, 4000, "자격 증명에 실패하였습니다."),

    // 5000 ~ Server Error
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5000, "정의되지 않은 에러가 발생하였습니다."),

    // 6000 ~ API Client Error
    EXTERNAL_API_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 6000, "외부 API 연동 중 알 수 없는 에러가 발생하였습니다."),

}
