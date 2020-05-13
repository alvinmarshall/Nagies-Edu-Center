package com.cheise_proj.presentation.viewmodel.files

import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import com.cheise_proj.domain.entity.files.FilesEntity
import com.cheise_proj.domain.usecase.files.GetVideoTask
import com.cheise_proj.presentation.extensions.asVideoPresentation
import com.cheise_proj.presentation.extensions.asVideoPresentationList
import com.cheise_proj.presentation.model.files.Video
import com.cheise_proj.presentation.model.vo.Resource
import com.cheise_proj.presentation.utils.IServerPath
import com.cheise_proj.presentation.viewmodel.BaseViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

class VideoViewModel @Inject constructor(
    private val getVideoTask: GetVideoTask,
    private val serverPath: IServerPath
) : BaseViewModel() {

    fun getVideos(): LiveData<Resource<List<Video>>> {
        return getVideoTask.buildUseCase()
            .map { t: List<FilesEntity> ->
                t.forEach {
                    it.path = it.photo
                    it.photo = serverPath.setCorrectPath(it.photo)
                }
                t.asVideoPresentationList()
            }
            .map { t: List<Video> ->
                Resource.onSuccess(t)
            }
            .startWith(Resource.onLoading())
            .onErrorResumeNext(
                Function {
                    Observable.just(Resource.onError(it.localizedMessage))
                }
            )
            .toFlowable(BackpressureStrategy.LATEST)
            .toLiveData()
    }

    fun getVideo(identifier: String): LiveData<Resource<Video>> {
        return getVideoTask.buildUseCase(identifier)
            .map { t: List<FilesEntity> ->
                t[0].asVideoPresentation()
            }
            .map { t: Video ->
                Resource.onSuccess(t)
            }
            .startWith(Resource.onLoading())
            .onErrorResumeNext(
                Function {
                    Observable.just(Resource.onError(it.localizedMessage))
                }
            )
            .toFlowable(BackpressureStrategy.LATEST)
            .toLiveData()
    }
}