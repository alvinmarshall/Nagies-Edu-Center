package com.cheise_proj.domain.entity.files

interface IFilesEntity {
    val id: Int
    val studentName: String
    val teacherName: String
    var photo: String?
    val date: String
    var path: String?
    val refNo: String
}