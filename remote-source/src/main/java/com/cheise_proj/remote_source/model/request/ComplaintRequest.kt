package com.cheise_proj.remote_source.model.request

import com.google.gson.annotations.SerializedName

data class ComplaintRequest(
    @SerializedName("message")
    val content: String,
    @SerializedName("teacherName")
    val identifier: String?
)