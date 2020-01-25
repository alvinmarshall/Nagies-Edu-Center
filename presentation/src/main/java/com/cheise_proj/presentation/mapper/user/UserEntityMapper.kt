package com.cheise_proj.presentation.mapper.user

import com.cheise_proj.domain.entity.user.UserEntity
import com.cheise_proj.presentation.mapper.PresentationMapper
import com.cheise_proj.presentation.model.user.User
import javax.inject.Inject

class UserEntityMapper @Inject constructor() : PresentationMapper<User, UserEntity> {
    override fun presentationToEntity(p: User): UserEntity {
        return UserEntity(
            uuid = p.uuid,
            username = p.username,
            photo = p.photo,
            password = p.password,
            token = p.token,
            role = p.role,
            level = p.level,
            name = p.name
        )
    }

    override fun entityToPresentation(e: UserEntity): User {
        return User(
            uuid = e.uuid,
            username = e.username,
            photo = e.photo,
            password = e.password,
            token = e.token,
            role = e.role,
            level = e.level,
            name = e.name
        )
    }
}