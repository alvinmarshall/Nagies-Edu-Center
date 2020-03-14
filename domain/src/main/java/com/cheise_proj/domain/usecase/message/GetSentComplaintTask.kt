package com.cheise_proj.domain.usecase.message

import com.cheise_proj.domain.entity.message.ComplaintEntity
import com.cheise_proj.domain.repository.MessageRepository
import com.cheise_proj.domain.rx.ObservableUseCase
import com.cheise_proj.domain.rx.qualifier.Background
import com.cheise_proj.domain.rx.qualifier.Foreground
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class GetSentComplaintTask @Inject constructor(
    private val messageRepository: MessageRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler) :
    ObservableUseCase<List<ComplaintEntity>, String>(backgroundScheduler, foregroundScheduler) {
    override fun generateSingle(input: String?): Observable<List<ComplaintEntity>> {
        input?.let { return messageRepository.getComplaint(it) }?:return messageRepository.getSentComplaints()
    }
}