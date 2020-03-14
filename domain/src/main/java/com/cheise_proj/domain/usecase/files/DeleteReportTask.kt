package com.cheise_proj.domain.usecase.files

import com.cheise_proj.domain.repository.FilesRepository
import com.cheise_proj.domain.rx.ObservableUseCase
import com.cheise_proj.domain.rx.qualifier.Background
import com.cheise_proj.domain.rx.qualifier.Foreground
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class DeleteReportTask @Inject constructor(
    private val filesRepository: FilesRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
) :
    ObservableUseCase<Boolean, DeleteReportTask.Params>(backgroundScheduler, foregroundScheduler) {
    inner class Params(val identifier: String, val url: String)

    override fun generateSingle(input: Params?): Observable<Boolean> {
        input?.run {
            return filesRepository.deleteReport(identifier, url)
        } ?: throw IllegalArgumentException("DeleteReportTask params can't be null")
    }
}