package com.cheise_proj.remote_source.model.dto.message

import com.google.gson.annotations.SerializedName

data class ComplaintsDto(
    @SerializedName("complaints")
    val complaint: List<ComplaintDto>
)

data class ComplaintDto(
    @SerializedName("studentNo")
    val refNo: String,
    val studentName: String,
    val level: String,
    @SerializedName("guardianContact")
    val contact: String,
    @SerializedName("teacherName")
    val receiver: String,
    @SerializedName("uid")
    val id: Int,
    @SerializedName("guardianName")
    val sender: String,
    @SerializedName("message")
    val content: String,
    val date: String
)