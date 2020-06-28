package com.cheise_proj.domain.usecase.message

import com.cheise_proj.domain.repository.MessageRepository
import com.cheise_proj.domain.rx.ObservableUseCase
import com.cheise_proj.domain.rx.qualifier.Background
import com.cheise_proj.domain.rx.qualifier.Foreground
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class SendMessageTask @Inject constructor(
    private val messageRepository: MessageRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
) :
    ObservableUseCase<Boolean, SendMessageTask.Params>(backgroundScheduler, foregroundScheduler) {
    inner class Params(
        val content: String,
        val receiverName: String?,
        val identifier: String?
    )

    override fun generateSingle(input: Params?): Observable<Boolean> {
        input?.run {
            return messageRepository.sendMessage(content, receiverName, identifier)
        } ?: throw IllegalArgumentException("SendMessageTask params can't be null")
    }
}