package com.ludin.springboot.kotlin.assignmentapi.infrastructure.rest.baseSite

import com.ludin.springboot.kotlin.assignmentapi.common.exception.ApiException
import com.ludin.springboot.kotlin.assignmentapi.common.logger
import com.ludin.springboot.kotlin.assignmentapi.common.model.ApiCode
import com.ludin.springboot.kotlin.assignmentapi.config.SecurityProperties
import com.ludin.springboot.kotlin.assignmentapi.infrastructure.rest.baseSite.request.BaseClientLoginRequest
import com.ludin.springboot.kotlin.assignmentapi.infrastructure.rest.baseSite.request.FetchBaseDataRequest
import com.ludin.springboot.kotlin.assignmentapi.infrastructure.rest.baseSite.response.BaseClientLoginResponse
import com.ludin.springboot.kotlin.assignmentapi.infrastructure.rest.baseSite.response.FetchBaseDataResponse
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.slf4j.MDCContext
import org.springframework.stereotype.Component

@Component
class BaseClient(
    private val client: HttpClient,
    private val securityProperties: SecurityProperties,
) {
    companion object {
        const val BASE_URI = "/api"
        const val LOGIN_URI = "/login"
    }

    private val log = logger()


    fun fetchBaseData(req: FetchBaseDataRequest): FetchBaseDataResponse = runBlocking(MDCContext()) {

        try {
            val token = getBaseClientToken(
                req = BaseClientLoginRequest(
                    secret = securityProperties.baseClientSecret
                )
            ).token

            val res = client.get {
                url("${securityProperties.baseClientUrl}$BASE_URI")
                setBody(req)
                header("Authorization", "Bearer $token")
            }

            if (res.status == HttpStatusCode.OK) {
                val result: FetchBaseDataResponse = res.body()
                log.info("데이터 수집 성공")
                result
            } else {
                throw ApiException(ApiCode.NOT_FOUND)
            }

        } catch (e: ApiException) {
            if (e.apiCode != ApiCode.NOT_FOUND) log.error("데이터를 찾을 수 없습니다.")
            throw e
        } catch (e: Exception) {
            log.error("데이터 서버 에러", e)
            throw ApiException(ApiCode.EXTERNAL_API_UNKNOWN_ERROR)
        }
    }

    fun getBaseClientToken(req: BaseClientLoginRequest): BaseClientLoginResponse = runBlocking(MDCContext()) {
        try {
            val res = client.post {
                url("${securityProperties.baseClientUrl}$BASE_URI$LOGIN_URI")
                setBody(req)
            }

            if (res.status == HttpStatusCode.OK) {
                val result: BaseClientLoginResponse = res.body()
                log.info("로그인 성공")

                result
            } else {
                throw ApiException(ApiCode.AUTHENTICATION_FAILED)
            }
        } catch (e: ApiException) {
            log.error("로그인 실패, 아이디와 패스워드를 확인해주세요")
            throw e
        } catch (e: Exception) {
            log.error("로그인 서버 에러", e)
            throw ApiException(ApiCode.EXTERNAL_API_UNKNOWN_ERROR)
        }
    }

}