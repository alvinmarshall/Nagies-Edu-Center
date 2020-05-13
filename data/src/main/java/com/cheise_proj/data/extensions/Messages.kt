package com.cheise_proj.data.extensions

import com.cheise_proj.data.mapper.message.MessageDataEntityMapper
import com.cheise_proj.data.model.message.MessageData
import com.cheise_proj.domain.entity.message.MessageEntity

fun MessageData.asEntity() = MessageDataEntityMapper().dataToEntity(this)

fun MessageEntity.asData() = MessageDataEntityMapper().entityToData(this)


fun List<MessageEntity>.asDataList(): List<MessageData> =
    MessageDataEntityMapper().entityToDataList(this)

fun List<MessageData>.asEntityList(): List<MessageEntity> =
    MessageDataEntityMapper().dataToEntityList(this)
