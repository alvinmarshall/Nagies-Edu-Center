package com.cheise_proj.data.extensions

import com.cheise_proj.data.mapper.people.PeopleDataEntityMapper
import com.cheise_proj.data.model.people.PeopleData
import com.cheise_proj.domain.entity.people.PeopleEntity

fun PeopleData.asEntity() = PeopleDataEntityMapper().dataToEntity(this)

fun PeopleEntity.asData() = PeopleDataEntityMapper().entityToData(this)


fun List<PeopleData>.asEntityList(): List<PeopleEntity> =
    PeopleDataEntityMapper().dataToEntityList(this)

fun List<PeopleEntity>.asDataList(): List<PeopleData> =
    PeopleDataEntityMapper().entityToDataList(this)