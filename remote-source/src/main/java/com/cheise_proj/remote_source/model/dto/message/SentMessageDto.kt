package com.cheise_proj.remote_source.model.dto.message

import com.google.gson.annotations.SerializedName

data class SentMessageDto(
    @SerializedName("data")
    val sentResponse: SentMessage
)

data class SentMessage(
    val status: Int,
    val title: String?
)