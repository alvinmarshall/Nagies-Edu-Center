package utils

import com.cheise_proj.data.model.message.ComplaintData
import com.cheise_proj.data.model.message.MessageData

object TestMessageGenerator {
    fun getRemoteMessage(): List<MessageData> {
        return arrayListOf(
            MessageData(
                uid = 1,
                sender = "test sender",
                content = "test content",
                date = "test date"
            ), MessageData(
                uid = 2,
                sender = "test sender2",
                content = "test content2",
                date = "test date2"
            )

        )
    }

    fun getLocalMessage(): List<MessageData> {
        return arrayListOf(
            MessageData(
                uid = 1,
                sender = "test sender",
                content = "test content",
                date = "test date"
            )

        )
    }

    fun getComplaint(): List<ComplaintData> {
        return arrayListOf(
            ComplaintData(
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