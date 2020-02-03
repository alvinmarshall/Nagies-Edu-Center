package com.cheise_proj.remote_source.mapper.message

import com.cheise_proj.data.model.message.MessageData
import com.cheise_proj.remote_source.mapper.RemoteMapper
import com.cheise_proj.remote_source.model.dto.message.MessageDto
import javax.inject.Inject

class MessageDtoDataMapper @Inject constructor() :
    RemoteMapper<MessageDto, MessageData> {

    override fun dtoToData(t: MessageDto): MessageData {
        return MessageData(
            uid = t.uid,
            sender = t.sender,
            content = t.content,
            date = t.date,
            timestamp = t.timestamp
        )
    }

    override fun dataToDto(d: MessageData): MessageDto {
        return MessageDto(
            uid = d.uid,
            sender = d.sender,
            content = d.content,
            date = d.date,
            timestamp = d.timestamp
        )
    }

    override fun dtoToDataList(tList: List<MessageDto>): List<MessageData> {
        val list = mutableListOf<MessageData>()
        tList.forEach {
            list.add(dtoToData(it))
        }
        return list
    }

    override fun dataToDtoList(dList: List<MessageData>): List<MessageDto> {
        val list = mutableListOf<MessageDto>()
        dList.forEach {
            list.add(dataToDto(it))
        }
        return list
    }



}