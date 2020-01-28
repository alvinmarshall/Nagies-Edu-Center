package com.cheise_proj.domain.entity.user

data class ProfileEntity(
    override val refNo: String,
    override val name: String,
    override val gender: String,
    override val adminDate: String,
    override val faculty: String,
    override val level: String,
    override val username: String,
    override val contact: String,
    override var imageUrl: String?,
    override val section: String,
    override val semester: String,
    override val guardian: String,
    override val dob: String
) :IProfile