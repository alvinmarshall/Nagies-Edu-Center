package com.cheise_proj.data.model.user

data class UserData(
    var username: String,
    var password: String,
    val uuid: Int,
    val role: String,
    var photo: String?,
    val name: String,
    val level: String,
    val token: String
){
//    constructor():this("","",0,"","","","","")
}