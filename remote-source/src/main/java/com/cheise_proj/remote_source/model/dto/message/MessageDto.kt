package com.cheise_proj.remote_source.model.dto.message

import com.google.gson.annotations.SerializedName

data class MessagesDto(
    @SerializedName("messages")
    val message: List<MessageDto>
)

data class MessageDto (
    val uid: Int,
    val sender: String,
    val content: String,
    val date: String
)