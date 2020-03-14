package com.cheise_proj.local_source.utils

import com.cheise_proj.local_source.model.message.ComplaintLocal
import com.cheise_proj.local_source.model.message.MessageLocal

object TestMessageGenerator {
    fun getMessages(): List<MessageLocal> {
        return arrayListOf(
            MessageLocal(
                uid = 1,
                sender = "test sender",
                content = "test content local",
                date = "test date"
            ), MessageLocal(
                uid = 2,
                sender = "test sender2",
                content = "test content2 local",
                date = "test date2"
            )

        )
    }

    fun getMessage(): MessageLocal {
        return MessageLocal(
            uid = 1,
            sender = "test sender",
            content = "test content local",
            date = "test date"
        )
    }

    fun getComplaint(): List<ComplaintLocal> {
        return arrayListOf(
            ComplaintLocal(
                id = 1,
                contact = "test contact",
                refNo = "test refNo",
                studentName = "test studentName",
                receiver = "test receiver",
                date = "test date",
                sender = "test sender",
                content = "test content",
                level = "test level"
            )
        )

    }
}