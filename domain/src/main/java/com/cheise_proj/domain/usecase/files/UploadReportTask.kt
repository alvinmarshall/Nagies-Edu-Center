package com.cheise_proj.domain.usecase.files

import com.cheise_proj.domain.repository.FilesRepository
import com.cheise_proj.domain.rx.ObservableUseCase
import com.cheise_proj.domain.rx.qualifier.Background
import com.cheise_proj.domain.rx.qualifier.Foreground
import io.reactivex.Observable
import io.reactivex.Scheduler
import okhttp3.MultipartBody
import javax.inject.Inject

class UploadReportTask @Inject constructor(
    private val filesRepository: FilesRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
) :
    ObservableUseCase<Int, UploadReportTask.Params>(backgroundScheduler, foregroundScheduler) {
    inner class Params(
        val file: MultipartBody.Part,
        val refNo: MultipartBody.Part,
        val fullName: MultipartBody.Part
    )

    override fun generateSingle(input: Params?): Observable<Int> {
        input?.let {
            with(it) {
                return filesRepository.uploadReport(file,refNo,fullName)
            }
        } ?: throw IllegalArgumentException("upload report params can't be null")
    }


}