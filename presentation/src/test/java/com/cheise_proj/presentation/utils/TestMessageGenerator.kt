package com.cheise_proj.presentation.utils

import com.cheise_proj.presentation.model.message.Complaint
import com.cheise_proj.presentation.model.message.Message

object TestMessageGenerator {
    fun getMessages(): List<Message> {
        return arrayListOf(
            Message(
                uid = 1,
                sender = "test sender",
                content = "test content",
                date = "test date"
            ), Message(
                uid = 2,
                sender = "test sender2",
                content = "test content2",
                date = "test date2"
            )

        )
    }

    fun getMessage(): List<Message> {
        return arrayListOf(
            Message(
                uid = 1,
                sender = "test sender",
                content = "test content",
                date = "test date"
            )

        )
    }

    fun getComplaint():List<Complaint>{
        return arrayListOf(
            Complaint(
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