package utils

import com.cheise_proj.domain.entity.message.MessageEntity

object TestMessageGenerator {
    fun getMessage(): List<MessageEntity> {
        return arrayListOf(
            MessageEntity(
                uid = 1,
                sender = "test sender",
                content = "test content",
                date = "test date"
            )
        )
    }
}