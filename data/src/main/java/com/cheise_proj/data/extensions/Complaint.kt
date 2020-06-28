package com.cheise_proj.data.extensions

import com.cheise_proj.data.mapper.message.ComplaintDataEntityMapper
import com.cheise_proj.data.model.message.ComplaintData
import com.cheise_proj.domain.entity.message.ComplaintEntity

internal fun ComplaintData.asEntity() = ComplaintDataEntityMapper().dataToEntity(this)

internal fun ComplaintEntity.asData() = ComplaintDataEntityMapper().entityToData(this)


internal fun List<ComplaintData>.asEntityList(): List<ComplaintEntity> =
    ComplaintDataEntityMapper().dataToEntityList(this)

internal fun List<ComplaintEntity>.asDataList(): List<ComplaintData> =
    ComplaintDataEntityMapper().entityToDataList(this)