package com.example.chatbot

import com.google.gson.annotations.SerializedName

data class RequestBody(

    @SerializedName("model")
    val model: String,

    @SerializedName("prompt")
    val prompt: String,

)