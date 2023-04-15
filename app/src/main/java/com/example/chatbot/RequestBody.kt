package com.example.chatbot

data class RequestBody(
    val logprobs: Any?=null,
    val max_tokens: Int,
    val model: String,
    val n: Int,
    val prompt: String,
    val stop: String,
    val stream: Boolean,
    val temperature: Int,
    val top_p: Int
)