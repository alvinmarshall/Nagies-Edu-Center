package utils

import com.cheise_proj.remote_source.model.dto.message.MessageDto
import com.cheise_proj.remote_source.model.dto.message.MessagesDto

object TestMessageGenerator {
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
}