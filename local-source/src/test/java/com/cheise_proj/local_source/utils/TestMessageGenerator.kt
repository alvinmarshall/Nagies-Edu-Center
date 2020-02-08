package com.cheise_proj.local_source.utils

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
}