package utils

import com.cheise_proj.domain.entity.message.ComplaintEntity
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

    fun getComplaint(): List<ComplaintEntity> {
        return arrayListOf(
            ComplaintEntity(
                uid = 1,
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