package com.ludin.springboot.kotlin.assignmentapi.controller

import com.ludin.springboot.kotlin.assignmentapi.common.model.ApiResponse
import com.ludin.springboot.kotlin.assignmentapi.controller.resquest.BaseGetRequest
import com.ludin.springboot.kotlin.assignmentapi.service.BaseService
import com.ludin.springboot.kotlin.assignmentapi.service.response.BaseGetResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.security.access.prepost.PreAuthorize

@RestController
@RequestMapping("/api/base")
//@PreAuthorize("hasRole('')") //인증
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "기본 컨트롤러")
class BaseController(
    private val baseService: BaseService
) {

    @GetMapping()
    @Operation(summary = "기본 GET Method API")
    fun baseGet(req: BaseGetRequest): ApiResponse<BaseGetResponse> = ApiResponse(data = baseService.baseGet(req))

}