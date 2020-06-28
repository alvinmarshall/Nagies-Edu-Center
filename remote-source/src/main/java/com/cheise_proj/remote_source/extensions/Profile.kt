package com.cheise_proj.remote_source.extensions

import com.cheise_proj.data.model.user.ProfileData
import com.cheise_proj.remote_source.mapper.user.ProfileDtoDataMapper
import com.cheise_proj.remote_source.model.dto.user.IProfileDto


internal fun IProfileDto.asData(): ProfileData = ProfileDtoDataMapper().dtoToData(this)


internal fun ProfileData.asDTO() = ProfileDtoDataMapper().dataToDto(this)


