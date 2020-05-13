package com.cheise_proj.data.mapper.message

import com.cheise_proj.data.mapper.base.DataListMapper
import com.cheise_proj.data.mapper.base.DataMapper
import com.cheise_proj.data.model.message.MessageData
import com.cheise_proj.domain.entity.message.MessageEntity

class MessageDataEntityMapper : DataMapper<MessageData, MessageEntity>,DataListMapper<MessageData, MessageEntity> {
    override fun dataToEntity(d: MessageData): MessageEntity {
        return MessageEntity(
            uid = d.uid,
            sender = d.sender,
            date = d.date,
            content = d.content
        )
    }

    override fun entityToData(e: MessageEntity): MessageData {
        return MessageData(
            uid = e.uid,
            sender = e.sender,
            date = e.date,
            content = e.content
        )
    }

    override fun dataToEntityList(dList: List<MessageData>): List<MessageEntity> {
        val list = mutableListOf<MessageEntity>()
        dList.forEach {
            list.add(dataToEntity(it))
        }
        return list
    }

    override fun entityToDataList(eList: List<MessageEntity>): List<MessageData> {
        val list = mutableListOf<MessageData>()
        eList.forEach {
            list.add(entityToData(it))
        }
        return list
    }
}