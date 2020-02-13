package com.cheise_proj.presentation.model.people

data class People(
    val id: Int,
    val refNo: String,
    val name: String,
    val photo: String?,
    val username: String,
    val gender: String,
    val contact: String?
)