package com.cheise_proj.remote_source.model.dto.files

import com.google.gson.annotations.SerializedName


data class TimeTablesDto(
    val data: List<TimeTableDto>
)

data class TimeTableDto(
    override val id: Int,
    override var studentName: String,
    @SerializedName("teacherEmail")
    override var teacherName: String,
    @SerializedName("fileUrl")
    override var photo: String?,
    override val date: String,
    override var path: String?,
    @SerializedName("studentNo")
    override val refNo: String
) : IFilesDto