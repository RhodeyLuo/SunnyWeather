package com.rhodey.sunnyweather.logic.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    private const val BASE_URL = "https://api.caiyunapp.com/"
    private val logInterceptor =
        HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY }

    private val client = OkHttpClient.Builder().addInterceptor(logInterceptor).build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(service: Class<T>): T = retrofit.create(service)

    inline fun <reified T> create(): T = create(T::class.java)
}