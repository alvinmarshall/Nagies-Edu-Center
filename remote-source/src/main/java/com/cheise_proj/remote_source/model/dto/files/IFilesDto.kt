package com.cheise_proj.remote_source.model.dto.files

interface IFilesDto {
    val id: Int
    var studentName: String
    var teacherName: String
    var photo: String?
    val date: String
    var path: String?
    val refNo: String
}