package com.cheise_proj.remote_source.model.request

import com.google.gson.annotations.SerializedName

data class MessageRequest(
    @SerializedName("message")
    val content: String,
    @SerializedName("target_name")
    val receiverName:String?,
    @SerializedName("target_id")
    val identifier: String?
)