package utils

import com.cheise_proj.data.model.message.MessageData

object TestMessageGenerator {
    fun getRemoteMessage(): List<MessageData> {
        return arrayListOf(
            MessageData(
                uid = 1,
                sender = "test sender",
                content = "test content",
                date = "test date",
                timestamp = System.currentTimeMillis()
            ), MessageData(
                uid = 2,
                sender = "test sender2",
                content = "test content2",
                date = "test date2",
                timestamp = System.currentTimeMillis()
            )

        )
    }

    fun getLocalMessage(): List<MessageData> {
        return arrayListOf(
            MessageData(
                uid = 1,
                sender = "test sender",
                content = "test content",
                date = "test date",
                timestamp = 0
            )

        )
    }
}