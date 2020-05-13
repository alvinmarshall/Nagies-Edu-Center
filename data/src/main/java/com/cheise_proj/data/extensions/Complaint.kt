package com.cheise_proj.data.extensions

import com.cheise_proj.data.mapper.message.ComplaintDataEntityMapper
import com.cheise_proj.data.model.message.ComplaintData
import com.cheise_proj.domain.entity.message.ComplaintEntity

fun ComplaintData.asEntity() = ComplaintDataEntityMapper().dataToEntity(this)

fun ComplaintEntity.asData() = ComplaintDataEntityMapper().entityToData(this)


fun List<ComplaintData>.asEntityList(): List<ComplaintEntity> =
    ComplaintDataEntityMapper().dataToEntityList(this)

fun List<ComplaintEntity>.asDataList(): List<ComplaintData> =
    ComplaintDataEntityMapper().entityToDataList(this)