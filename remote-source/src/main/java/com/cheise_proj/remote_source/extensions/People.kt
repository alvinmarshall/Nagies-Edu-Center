package com.cheise_proj.remote_source.extensions

import com.cheise_proj.data.model.people.PeopleData
import com.cheise_proj.remote_source.mapper.people.PeopleDtoDataMapper
import com.cheise_proj.remote_source.model.dto.people.IPeopleDto

internal fun PeopleData.asDTO() = PeopleDtoDataMapper().dataToDto(this)

internal fun IPeopleDto.asData() = PeopleDtoDataMapper().dtoToData(this)


internal fun List<PeopleData>.asDTOList(): List<IPeopleDto> =
    PeopleDtoDataMapper().dataToDtoList(this)

internal fun List<IPeopleDto>.asDataList(): List<PeopleData> =
    PeopleDtoDataMapper().dtoToDataList(this)