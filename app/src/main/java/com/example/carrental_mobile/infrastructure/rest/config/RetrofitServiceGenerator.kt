package com.example.carrental_mobile.infrastructure.rest.config

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitServiceGenerator {

    private val BASE_URL: String = "http://10.0.2.2:8090"

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())

    private var retrofit = builder.build()

    private val httpClient = OkHttpClient.Builder()

    private val logging = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    fun <S> createService(serviceClass: Class<S>, token: String): S {
        httpClient.interceptors().clear()
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val builder1 = original.newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Auth-Token", token)
            val request = builder1.build()
            chain.proceed(request)
        }

        return createService(
            serviceClass
        )
    }

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.addLogging().create(serviceClass)
    }

    private fun Retrofit.addLogging(): Retrofit {
        if (!httpClient.interceptors().contains(
                logging
            )) {
            httpClient.addInterceptor(
                logging
            )
            builder.client(httpClient.build())
            retrofit = builder.build()
        }

        return retrofit
    }
}