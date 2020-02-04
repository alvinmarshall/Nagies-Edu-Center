package com.cheise_proj.remote_source.model.dto.files

import com.google.gson.annotations.SerializedName

data class CircularsDto(
    @SerializedName("data")
    val circular: List<CircularDto>
)

data class CircularDto(
    val id: Int,
    @SerializedName("cid")
    val teacherName: String,
    @SerializedName("fileUrl")
    var photo: String?,
    val date: String
)