package com.cheise_proj.presentation.model.user

data class User(
    val username: String,
    var password: String,
    val uuid: Int,
    val role: String,
    var photo: String?,
    val name: String,
    val level: String,
    val token: String
)