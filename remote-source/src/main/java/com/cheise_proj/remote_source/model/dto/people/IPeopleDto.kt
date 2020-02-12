package com.cheise_proj.remote_source.model.dto.people

interface IPeopleDto {
    val id: Int
    val refNo: String
    val name: String
    val photo: String?
    val username: String
    val gender: String
    val contact:String?
}