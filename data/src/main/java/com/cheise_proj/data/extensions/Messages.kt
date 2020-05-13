package com.cheise_proj.data.extensions

import com.cheise_proj.data.mapper.message.MessageDataEntityMapper
import com.cheise_proj.data.model.message.MessageData
import com.cheise_proj.domain.entity.message.MessageEntity

internal fun MessageData.asEntity() = MessageDataEntityMapper().dataToEntity(this)

internal fun MessageEntity.asData() = MessageDataEntityMapper().entityToData(this)


internal fun List<MessageEntity>.asDataList(): List<MessageData> =
    MessageDataEntityMapper().entityToDataList(this)

internal fun List<MessageData>.asEntityList(): List<MessageEntity> =
    MessageDataEntityMapper().dataToEntityList(this)
