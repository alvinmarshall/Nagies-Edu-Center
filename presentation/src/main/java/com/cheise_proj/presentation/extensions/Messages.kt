package com.cheise_proj.presentation.extensions

import com.cheise_proj.domain.entity.message.MessageEntity
import com.cheise_proj.presentation.mapper.message.MessageEntityMapper
import com.cheise_proj.presentation.model.message.Message

internal fun Message.asEntity() = MessageEntityMapper().presentationToEntity(this)

internal fun MessageEntity.asPresentation() = MessageEntityMapper().entityToPresentation(this)


internal fun List<MessageEntity>.asPresentationList(): List<Message> =
    MessageEntityMapper().entityToPresentationList(this)

internal fun List<Message>.asEntityList(): List<MessageEntity> =
    MessageEntityMapper().presentationToEntityList(this)
