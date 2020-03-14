package utils

import com.cheise_proj.remote_source.model.dto.message.*
import com.cheise_proj.remote_source.model.request.ComplaintRequest
import com.cheise_proj.remote_source.model.request.MessageRequest

object TestMessageGenerator {

    fun getMessageRequest(): MessageRequest {
        return MessageRequest(
            content = "test content",
            identifier = "test identifier",
            receiverName = "test receiver name"
        )
    }

    fun getSentMessageDto(): SentMessageDto {
        return SentMessageDto(
            SentMessage(status = 200, title = "message sent")
        )
    }

    fun getComplaintRequest(): ComplaintRequest {
        return ComplaintRequest(
            content = "test content",
            identifier = "test identifier"
        )
    }

    fun getMessageDto(): MessagesDto {
        return MessagesDto(
            arrayListOf(
                MessageDto(
                    uid = 1,
                    sender = "test sender",
                    content = "test content remote",
                    date = "test date"
                ),
                MessageDto(
                    uid = 2,
                    sender = "test sender2",
                    content = "test content remote",
                    date = "test date"
                )
            )
        )
    }

    fun getComplaintDto(): ComplaintsDto {
        return ComplaintsDto(
            complaint = arrayListOf(
                ComplaintDto(
                    id = 1,
                    level = "test level",
                    content = "test content",
                    sender = "test sender",
                    date = "test date",
                    receiver = "test teacherName",
                    studentName = "test studentName",
                    refNo = "test refNo",
                    contact = "test contact"
                )
            )

        )
    }
}