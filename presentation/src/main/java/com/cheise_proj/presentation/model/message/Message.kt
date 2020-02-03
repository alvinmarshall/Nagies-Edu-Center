package com.cheise_proj.presentation.model.message

data class Message(
    val uid: Int,
    val sender: String,
    val content: String,
    val date: String,
    val timestamp: Long
)