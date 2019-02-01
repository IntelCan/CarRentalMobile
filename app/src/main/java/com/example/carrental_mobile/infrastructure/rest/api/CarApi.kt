package com.example.carrental_mobile.infrastructure.rest.api

import com.example.carrental_mobile.domain.car.Car
import com.example.carrental_mobile.infrastructure.rest.config.RetrofitServiceGenerator.createService
import io.reactivex.Observable
import retrofit2.http.GET

interface CarApi {

    @GET("/api/car/all")
    fun getCars(): Observable<List<Car>>


    companion object {
        fun create(token: String): CarApi {
            return createService(CarApi::class.java, token)
        }
    }
}