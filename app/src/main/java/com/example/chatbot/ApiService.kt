package com.example.chatbot

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("completions")
    fun sendReq(@Header("Content-Type") contentType:String, @Header("Authorization") key:String ,@Body requestBody: RequestBody): Call<ResponseData>

}