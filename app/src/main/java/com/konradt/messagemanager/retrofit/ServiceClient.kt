package com.konradt.messagemanager.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceClient {

    private const val URL = "https://run.mocky.io/v3/6125f2d0-0688-4547-aae8-0295d984f196/"

    private val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val builder = Retrofit.Builder()
        .baseUrl(URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> getService(serviceClass: Class<T>): T {
        return builder.create(serviceClass)
    }
}