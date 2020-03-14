package com.cheise_proj.remote_source.model.dto.files

import com.google.gson.annotations.SerializedName

data class BillsDto(
    val data: List<BillDto>
)

data class BillDto(
    override val id: Int,
    override var studentName: String,
    @SerializedName("sender")
    override var teacherName: String,
    @SerializedName("fileUrl")
    override var photo: String?,
    override val date: String,
    override var path: String?,
    @SerializedName("studentNo")
    override val refNo: String

) : IFilesDto