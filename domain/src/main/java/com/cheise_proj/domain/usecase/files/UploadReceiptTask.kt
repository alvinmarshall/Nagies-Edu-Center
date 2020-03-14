package com.cheise_proj.domain.usecase.files

import com.cheise_proj.domain.repository.FilesRepository
import com.cheise_proj.domain.rx.ObservableUseCase
import com.cheise_proj.domain.rx.qualifier.Background
import com.cheise_proj.domain.rx.qualifier.Foreground
import io.reactivex.Observable
import io.reactivex.Scheduler
import okhttp3.MultipartBody
import javax.inject.Inject

class UploadReceiptTask @Inject constructor(
    private val filesRepository: FilesRepository,
    @Background backgroundScheduler: Scheduler, @Foreground foregroundScheduler: Scheduler
) :
    ObservableUseCase<Int, UploadReceiptTask.Params>(backgroundScheduler, foregroundScheduler) {
    class Params(
        val file: MultipartBody.Part
    )

    override fun generateSingle(input: Params?): Observable<Int> {
        if (input == null) throw IllegalArgumentException("params can't be null")
        return filesRepository.uploadReceipt(input.file)

    }
}