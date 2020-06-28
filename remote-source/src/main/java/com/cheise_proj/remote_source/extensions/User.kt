package com.cheise_proj.remote_source.extensions

import com.cheise_proj.data.model.user.UserData
import com.cheise_proj.remote_source.mapper.user.UserDtoDataMapper
import com.cheise_proj.remote_source.model.dto.user.UserDto

internal fun UserDto.asData()  = UserDtoDataMapper().dtoToData(this)


internal fun UserData.asDTO() = UserDtoDataMapper().dataToDto(this)

