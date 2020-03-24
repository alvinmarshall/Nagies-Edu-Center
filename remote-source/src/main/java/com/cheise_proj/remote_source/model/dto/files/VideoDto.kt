package com.cheise_proj.remote_source.model.dto.files

import com.google.gson.annotations.SerializedName

data class VideossDto(
    val data: List<VideoDtoDto>
)

data class VideoDtoDto(
    override val id: Int,
    @SerializedName("recipient")
    override var studentName: String,
    @SerializedName("sender")
    override var teacherName: String,
    @SerializedName("fileUrl")
    override var photo: String?,
    override val date: String,
    override var path: String?,
    override val refNo: String

) : IFilesDto