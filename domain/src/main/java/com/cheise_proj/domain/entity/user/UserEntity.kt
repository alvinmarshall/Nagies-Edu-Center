package com.cheise_proj.domain.entity.user


data class UserEntity(
    override val username: String,
    override var password: String,
    override val uuid: Int,
    override val role: String,
    override var photo: String?,
    override val name: String,
    override val level: String,
    override val token: String
) : IUser

