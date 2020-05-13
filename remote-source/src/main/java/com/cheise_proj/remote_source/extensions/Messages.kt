package com.cheise_proj.remote_source.extensions

import com.cheise_proj.data.model.message.MessageData
import com.cheise_proj.remote_source.mapper.message.MessageDtoDataMapper
import com.cheise_proj.remote_source.model.dto.message.MessageDto

internal fun MessageDto.asData() = MessageDtoDataMapper().dtoToData(this)

internal fun MessageData.asDTO() = MessageDtoDataMapper().dataToDto(this)


internal fun List<MessageDto>.asDataList(): List<MessageData> =
    MessageDtoDataMapper().dtoToDataList(this)

internal fun List<MessageDto>.asDTOList(): List<MessageData> =
    MessageDtoDataMapper().dtoToDataList(this)
