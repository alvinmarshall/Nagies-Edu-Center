package com.cheise_proj.data.extensions

import com.cheise_proj.data.mapper.user.UserDataEntityMapper
import com.cheise_proj.data.model.user.UserData
import com.cheise_proj.domain.entity.user.UserEntity

fun UserData.asEntity() = UserDataEntityMapper().dataToEntity(this)

fun UserEntity.asData() = UserDataEntityMapper().entityToData(this)