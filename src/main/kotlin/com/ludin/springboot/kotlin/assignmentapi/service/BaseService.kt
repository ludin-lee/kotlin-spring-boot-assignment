package com.ludin.springboot.kotlin.assignmentapi.service

import com.ludin.springboot.kotlin.assignmentapi.common.exception.ApiException
import com.ludin.springboot.kotlin.assignmentapi.common.model.ApiCode
import com.ludin.springboot.kotlin.assignmentapi.controller.resquest.BaseGetRequest
import com.ludin.springboot.kotlin.assignmentapi.infrastructure.rest.baseSite.BaseClient
import com.ludin.springboot.kotlin.assignmentapi.infrastructure.rest.baseSite.request.FetchBaseDataRequest
import com.ludin.springboot.kotlin.assignmentapi.infrastructure.rest.baseSite.response.FetchBaseDataResponse
import com.ludin.springboot.kotlin.assignmentapi.service.response.BaseGetResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class BaseService (
    private val baseClient: BaseClient
){

    @Transactional
    fun baseGet(req: BaseGetRequest): BaseGetResponse{
        val value : FetchBaseDataResponse = baseClient.fetchBaseData(FetchBaseDataRequest(
            key = req.key
        ))

        return BaseGetResponse(value.data ?: throw ApiException(ApiCode.NOT_FOUND))
    }
}