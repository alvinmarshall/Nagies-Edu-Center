package com.cheise_proj.local_source.extensions

import com.cheise_proj.data.model.user.UserData
import com.cheise_proj.local_source.mapper.user.UserLocalDataMapper
import com.cheise_proj.local_source.model.user.UserLocal

internal fun UserLocal.asData() = UserLocalDataMapper().localToData(this)

internal fun UserData.asLocal() = UserLocalDataMapper().dataToLocal(this)