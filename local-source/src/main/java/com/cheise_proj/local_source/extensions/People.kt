package com.cheise_proj.local_source.extensions

import com.cheise_proj.data.model.people.PeopleData
import com.cheise_proj.local_source.mapper.people.PeopleLocalDataMapper
import com.cheise_proj.local_source.model.people.PeopleLocal

internal fun PeopleLocal.asData() = PeopleLocalDataMapper().localToData(this)

internal fun PeopleData.asLocal() = PeopleLocalDataMapper().dataToLocal(this)


internal fun List<PeopleData>.asDataList(): List<PeopleLocal> =
    PeopleLocalDataMapper().dataToLocalList(this)

internal fun List<PeopleLocal>.asLocalList(): List<PeopleData> =
    PeopleLocalDataMapper().localToDataList(this)