package com.cheise_proj.local_source.extensions

import com.cheise_proj.data.model.message.ComplaintData
import com.cheise_proj.local_source.mapper.message.ComplaintLocalDataMapper
import com.cheise_proj.local_source.model.message.ComplaintLocal

internal fun ComplaintLocal.asData() = ComplaintLocalDataMapper().localToData(this)

internal fun ComplaintData.asLocal() = ComplaintLocalDataMapper().dataToLocal(this)


internal fun List<ComplaintLocal>.asDataList(): List<ComplaintData> =
    ComplaintLocalDataMapper().localToDataList(this)

internal fun List<ComplaintData>.asLocalList(): List<ComplaintLocal> =
    ComplaintLocalDataMapper().dataToLocalList(this)