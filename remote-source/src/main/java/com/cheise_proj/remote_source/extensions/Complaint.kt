package com.cheise_proj.remote_source.extensions

import com.cheise_proj.data.model.message.ComplaintData
import com.cheise_proj.remote_source.mapper.message.ComplaintDtoDataMapper
import com.cheise_proj.remote_source.model.dto.message.ComplaintDto

internal fun ComplaintDto.asData() = ComplaintDtoDataMapper().dtoToData(this)

internal fun ComplaintData.asDTO() = ComplaintDtoDataMapper().dataToDto(this)


internal fun List<ComplaintDto>.asDataList(): List<ComplaintData> =
    ComplaintDtoDataMapper().dtoToDataList(this)

internal fun List<ComplaintData>.asDTOList(): List<ComplaintDto> =
    ComplaintDtoDataMapper().dataToDtoList(this)