package com.example.chatbot

import com.google.gson.annotations.SerializedName

data class Usage(
    @SerializedName("completion_tokens")
    var completion_tokens: Int,
    @SerializedName("prompt_tokens")
    var prompt_tokens: Int,
    @SerializedName("total_tokens")
    var total_tokens: Int
)