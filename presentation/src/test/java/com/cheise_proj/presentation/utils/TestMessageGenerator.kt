package com.cheise_proj.presentation.utils

import com.cheise_proj.presentation.model.message.Message

object TestMessageGenerator {
    fun getMessages(): List<Message> {
        return arrayListOf(
            Message(
                uid = 1,
                sender = "test sender",
                content = "test content",
                date = "test date",
                timestamp = 0
            ), Message(
                uid = 2,
                sender = "test sender2",
                content = "test content2",
                date = "test date2",
                timestamp = 0
            )

        )
    }

    fun getMessage(): List<Message> {
        return arrayListOf(
            Message(
                uid = 1,
                sender = "test sender",
                content = "test content",
                date = "test date",
                timestamp = 0
            )

        )
    }
}