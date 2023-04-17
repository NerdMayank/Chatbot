package com.example.chatbot

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("completions")
    fun sendReq(@Body requestBody: RequestBody): Call<ResponseData>

}