package com.cheise_proj.presentation.model.vo

data class UserSession(
    val isLogin: Boolean,
    val role: String?,
    val username: String?,
    val level: String?
) {
    var uuid: Int = 0
    var photo: String? = ""
    var token: String? = ""
    var name: String? = ""
}