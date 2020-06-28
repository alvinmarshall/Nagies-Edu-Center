package com.cheise_proj.data.extensions

import com.cheise_proj.data.mapper.people.PeopleDataEntityMapper
import com.cheise_proj.data.model.people.PeopleData
import com.cheise_proj.domain.entity.people.PeopleEntity

internal fun PeopleData.asEntity() = PeopleDataEntityMapper().dataToEntity(this)

internal fun PeopleEntity.asData() = PeopleDataEntityMapper().entityToData(this)


internal fun List<PeopleData>.asEntityList(): List<PeopleEntity> =
    PeopleDataEntityMapper().dataToEntityList(this)

internal fun List<PeopleEntity>.asDataList(): List<PeopleData> =
    PeopleDataEntityMapper().entityToDataList(this)