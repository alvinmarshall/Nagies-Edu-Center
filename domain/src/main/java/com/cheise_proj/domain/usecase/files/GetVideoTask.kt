package com.cheise_proj.domain.usecase.files

import com.cheise_proj.domain.entity.files.FilesEntity
import com.cheise_proj.domain.repository.FilesRepository
import com.cheise_proj.domain.rx.ObservableUseCase
import com.cheise_proj.domain.rx.qualifier.Background
import com.cheise_proj.domain.rx.qualifier.Foreground
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class GetVideoTask @Inject constructor(
    private val filesRepository: FilesRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
) :
    ObservableUseCase<List<FilesEntity>, String>(backgroundScheduler, foregroundScheduler) {
    override fun generateSingle(input: String?): Observable<List<FilesEntity>> {
        if (input != null) return filesRepository.getVideo(input)
        return filesRepository.getVideos()
    }
}