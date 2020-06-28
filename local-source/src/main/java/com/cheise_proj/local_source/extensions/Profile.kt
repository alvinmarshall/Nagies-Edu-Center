package com.cheise_proj.local_source.extensions

import com.cheise_proj.data.model.user.ProfileData
import com.cheise_proj.local_source.mapper.user.ProfileLocalDataMapper
import com.cheise_proj.local_source.model.user.ProfileLocal

internal fun ProfileLocal.asData() = ProfileLocalDataMapper().localToData(this)


internal fun ProfileData.asLocal() = ProfileLocalDataMapper().dataToLocal(this)