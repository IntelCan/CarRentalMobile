package com.example.carrental_mobile.infrastructure.rest.api

import com.example.carrental_mobile.domain.customer.Customer
import com.example.carrental_mobile.infrastructure.rest.config.RetrofitServiceGenerator.createService
import io.reactivex.Observable
import retrofit2.http.GET

interface CustomerApi {

    @GET("/api/customer/all")
    fun getCustomers(): Observable<List<Customer>>


    companion object {

        fun create(token: String): CustomerApi {
            return createService(CustomerApi::class.java, token)
        }
    }
}