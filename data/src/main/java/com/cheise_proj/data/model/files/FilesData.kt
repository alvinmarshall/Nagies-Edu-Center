package com.cheise_proj.data.model.files

data class FilesData(
    val id: Int,
    val studentName: String,
    val teacherName: String,
    var photo: String?,
    val date: String,
    var path: String?,
    val refNo: String
)