package com.cheise_proj.domain.usecase.message

import com.cheise_proj.domain.repository.MessageRepository
import com.cheise_proj.domain.rx.ObservableUseCase
import com.cheise_proj.domain.rx.qualifier.Background
import com.cheise_proj.domain.rx.qualifier.Foreground
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class SendComplaintTask @Inject constructor(
    private val messageRepository: MessageRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
) :
    ObservableUseCase<Boolean, SendComplaintTask.Params>(backgroundScheduler, foregroundScheduler) {
    inner class Params(
        val content: String,
        val identifier: String?
    )

    override fun generateSingle(input: Params?): Observable<Boolean> {
        input?.run {
            return messageRepository.sendComplaint(content, identifier)
        } ?: throw IllegalArgumentException("SendComplaintTask params can't be null")
    }
}