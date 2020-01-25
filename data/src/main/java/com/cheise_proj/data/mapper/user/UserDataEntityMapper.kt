package com.cheise_proj.data.mapper.user

import com.cheise_proj.data.mapper.base.DataMapper
import com.cheise_proj.data.model.user.UserData
import com.cheise_proj.domain.entity.user.UserEntity
import javax.inject.Inject

class UserDataEntityMapper @Inject constructor() : DataMapper<UserData, UserEntity> {
    override fun dataToEntity(d: UserData): UserEntity {
        return UserEntity(
            username = d.username,
            uuid = d.uuid,
            photo = d.photo,
            role = d.role,
            level = d.level,
            password = d.password,
            name = d.name,
            token = d.token
        )
    }

    override fun entityToData(e: UserEntity): UserData {
        return UserData(
            username = e.username,
            uuid = e.uuid,
            photo = e.photo,
            role = e.role,
            level = e.level,
            password = e.password,
            name = e.name,
            token = e.token
        )
    }
}