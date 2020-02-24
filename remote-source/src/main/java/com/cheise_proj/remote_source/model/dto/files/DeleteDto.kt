package com.cheise_proj.remote_source.model.dto.files

import com.google.gson.annotations.SerializedName

data class DeleteDto(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int
)