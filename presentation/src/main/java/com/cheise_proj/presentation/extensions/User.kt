package com.cheise_proj.presentation.extensions

import com.cheise_proj.domain.entity.user.UserEntity
import com.cheise_proj.presentation.mapper.user.UserEntityMapper
import com.cheise_proj.presentation.model.user.User

internal fun User.asEntity() = UserEntityMapper().presentationToEntity(this)

internal fun UserEntity.asPresentation() = UserEntityMapper().entityToPresentation(this)