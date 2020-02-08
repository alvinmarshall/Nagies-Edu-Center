package com.cheise_proj.local_source.mapper.message

import com.cheise_proj.data.model.message.MessageData
import com.cheise_proj.local_source.mapper.base.LocalMapper
import com.cheise_proj.local_source.model.message.MessageLocal
import javax.inject.Inject

class MessageLocalDataMapper @Inject constructor() : LocalMapper<MessageLocal, MessageData> {
    override fun localToData(l: MessageLocal): MessageData {
        return MessageData(
            uid = l.uid,
            sender = l.sender,
            date = l.date,
            content = l.content
        )
    }

    override fun dataToLocal(d: MessageData): MessageLocal {
        return MessageLocal(
            uid = d.uid,
            sender = d.sender,
            date = d.date,
            content = d.content
        )
    }

    override fun localToDataList(lList: List<MessageLocal>): List<MessageData> {
        val list = arrayListOf<MessageData>()
        lList.forEach {

            list.add(localToData(it))
        }
        return list
    }

    override fun dataToLocalList(dList: List<MessageData>): List<MessageLocal> {
        val list = arrayListOf<MessageLocal>()
        dList.forEach {
            list.add(dataToLocal(it))
        }
        return list
    }
}