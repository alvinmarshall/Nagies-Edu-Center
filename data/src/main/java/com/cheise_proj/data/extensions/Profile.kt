package com.cheise_proj.data.extensions

import com.cheise_proj.data.mapper.user.ProfileDataEntityMapper
import com.cheise_proj.data.model.user.ProfileData
import com.cheise_proj.domain.entity.user.ProfileEntity

fun ProfileData.asEntity() = ProfileDataEntityMapper().dataToEntity(this)


fun ProfileEntity.asData() = ProfileDataEntityMapper().entityToData(this)