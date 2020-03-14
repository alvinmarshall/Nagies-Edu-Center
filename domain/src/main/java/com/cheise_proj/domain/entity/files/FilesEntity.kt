package com.cheise_proj.domain.entity.files

data class FilesEntity(
    override val id: Int,
    override val studentName: String,
    override val teacherName: String,
    override var photo: String?,
    override val date: String,
    override var path: String?,
    override val refNo: String

) : IFilesEntity