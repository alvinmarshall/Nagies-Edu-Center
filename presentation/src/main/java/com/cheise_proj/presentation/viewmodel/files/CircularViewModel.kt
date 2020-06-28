package com.cheise_proj.presentation.viewmodel.files

import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import com.cheise_proj.domain.entity.files.FilesEntity
import com.cheise_proj.domain.usecase.files.GetCircularTask
import com.cheise_proj.presentation.extensions.asCircularPresentation
import com.cheise_proj.presentation.extensions.asCircularPresentationList
import com.cheise_proj.presentation.model.files.Circular
import com.cheise_proj.presentation.model.vo.Resource
import com.cheise_proj.presentation.utils.IServerPath
import com.cheise_proj.presentation.viewmodel.BaseViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

class CircularViewModel @Inject constructor(
    private val getCircularTask: GetCircularTask,
    private val serverPath: IServerPath
) : BaseViewModel() {

    fun getCirculars(): LiveData<Resource<List<Circular>>> {
        return getCircularTask.buildUseCase()
            .map { t: List<FilesEntity> ->
                t.forEach {
                    it.path = it.photo
                    it.photo = serverPath.setCorrectPath(it.photo)
                }
                t.asCircularPresentationList()
            }
            .map { t: List<Circular> ->
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

    fun getCircular(identifier: String): LiveData<Resource<Circular>> {
        return getCircularTask.buildUseCase(identifier)
            .map { t: List<FilesEntity> ->
                t[0].asCircularPresentation()
            }
            .map { t: Circular ->
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