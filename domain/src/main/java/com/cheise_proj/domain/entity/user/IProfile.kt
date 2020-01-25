package com.cheise_proj.domain.entity.user

interface IProfile {
    val refNo: String
    val name: String
    val gender: String
    val dob: String
    val adminDate: String
    val section: String
    val faculty: String
    val level: String
    val semester: String?
    val username: String
    val guardian: String?
    val contact: String
    var imageUrl: String?
}