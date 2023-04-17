package com.example.chatbot

import com.google.gson.annotations.SerializedName

data class ResponseData(
    @SerializedName("choices")
    val choices: List<Choice>,
    @SerializedName("created")
    val created: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("usage")
    val usage: Usage
)