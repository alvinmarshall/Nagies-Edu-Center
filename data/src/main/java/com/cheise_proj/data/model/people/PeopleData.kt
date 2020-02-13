package com.cheise_proj.data.model.people

data class PeopleData(
    val id: Int,
    val refNo: String,
    val name: String,
    val photo: String?,
    val username: String,
    val gender: String,
    val contact: String?
)