package com.cheise_proj.presentation.mapper.message

import com.cheise_proj.domain.entity.message.MessageEntity
import com.cheise_proj.presentation.mapper.PresentationListMapper
import com.cheise_proj.presentation.model.message.Message

internal class MessageEntityMapper  : PresentationListMapper<Message, MessageEntity> {
    override fun presentationToEntity(p: Message): MessageEntity {
        return MessageEntity(
            uid = p.uid,
            content = p.content,
            date = p.date,
            sender = p.sender
        )
    }

    override fun entityToPresentation(e: MessageEntity): Message {
        return Message(
            uid = e.uid,
            content = e.content,
            date = e.date,
            sender = e.sender
        )
    }

    override fun presentationToEntityList(pList: List<Message>): List<MessageEntity> {
        val list = arrayListOf<MessageEntity>()
        pList.forEach {
            list.add(presentationToEntity(it))
        }
        return list
    }

    override fun entityToPresentationList(eList: List<MessageEntity>): List<Message> {
        val list = arrayListOf<Message>()
        eList.forEach {
            list.add(entityToPresentation(it))
        }
        return list
    }
}