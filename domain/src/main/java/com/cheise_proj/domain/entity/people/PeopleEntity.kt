package com.cheise_proj.domain.entity.people

data class PeopleEntity(
    override val id: Int,
    override val refNo: String,
    override val name: String,
    override val photo: String?,
    override val username: String,
    override val gender: String,
    override val contact: String?
) : IPeople