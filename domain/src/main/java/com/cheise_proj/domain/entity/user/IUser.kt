package com.cheise_proj.domain.entity.user

interface IUser {
    val username: String
    var password: String
    val uuid: Int
    val role: String
    var photo: String?
    val name: String
    val level: String
    val token:String
}