package com.cheise_proj.remote_source.model.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("uuid")
    val uuid: Int,
    val status: Int,
    val message: String,
    val token: String,
    val imageUrl: String?,
    val role: String,
    val name: String,
    val level: String
) {
    constructor() : this(0, 0, "", "", null, "", "", "")
}