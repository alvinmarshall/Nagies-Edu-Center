package com.cheise_proj.domain.usecase.message

import com.cheise_proj.domain.entity.message.MessageEntity
import com.cheise_proj.domain.repository.MessageRepository
import com.cheise_proj.domain.rx.ObservableUseCase
import com.cheise_proj.domain.rx.qualifier.Background
import com.cheise_proj.domain.rx.qualifier.Foreground
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class GetMessageTask @Inject constructor(
    private val messageRepository: MessageRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
) :
    ObservableUseCase<List<MessageEntity>, Int>(backgroundScheduler, foregroundScheduler) {
    override fun generateSingle(input: Int?): Observable<List<MessageEntity>> {
        return input?.let { messageRepository.getMessage(it) } ?: messageRepository.getMessages()
    }
}