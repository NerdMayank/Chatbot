package com.example.chatbot

import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private val client = OkHttpClient.Builder().addInterceptor {
        val newRequest:Request=it.request().newBuilder().addHeader("Content-Type","application/json")
            .addHeader("Authorization","Bearer YOUR_API_KEY").build()
        it.proceed(newRequest)
    }.build()


    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://api.openai.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}