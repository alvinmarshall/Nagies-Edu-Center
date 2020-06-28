package com.cheise_proj.presentation.extensions

import com.cheise_proj.domain.entity.user.ProfileEntity
import com.cheise_proj.presentation.mapper.user.ProfileEntityMapper
import com.cheise_proj.presentation.model.user.Profile

internal fun Profile.asEntity() = ProfileEntityMapper().presentationToEntity(this)


internal fun ProfileEntity.asPresentation() = ProfileEntityMapper().entityToPresentation(this)