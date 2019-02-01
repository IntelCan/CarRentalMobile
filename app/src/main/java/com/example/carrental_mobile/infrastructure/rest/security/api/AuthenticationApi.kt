package com.example.carrental_mobile.infrastructure.rest.security.api

import com.example.carrental_mobile.infrastructure.rest.config.RetrofitServiceGenerator
import com.example.carrental_mobile.infrastructure.rest.security.LoginCredentials
import com.example.carrental_mobile.infrastructure.rest.security.Token
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApi {

    @POST("/auth")
    fun login(@Body loginCredentials: LoginCredentials): Observable<Token>

    companion object {

        fun create(): AuthenticationApi {
            return RetrofitServiceGenerator.createService(AuthenticationApi::class.java)
        }

        fun create(token: String): AuthenticationApi {
            return RetrofitServiceGenerator.createService(AuthenticationApi::class.java, token)
        }
    }
}