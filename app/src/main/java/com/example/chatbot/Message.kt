package com.example.chatbot

data class Message(
    var message: String,
    var SENT_BY_BOT:Boolean=false
//false-> Sent By me, true-> sent bot
)
