package com.cheise_proj.presentation.viewmodel.files

import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import com.cheise_proj.domain.entity.files.FilesEntity
import com.cheise_proj.domain.usecase.files.GetTimeTableTask
import com.cheise_proj.presentation.mapper.files.TimeTableEntityMapper
import com.cheise_proj.presentation.model.files.TimeTable
import com.cheise_proj.presentation.model.vo.Resource
import com.cheise_proj.presentation.utils.IServerPath
import com.cheise_proj.presentation.viewmodel.BaseViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

class TimeTableViewModel @Inject constructor(
    private val getTimeTableTask: GetTimeTableTask,
    private val timeTableEntityMapper: TimeTableEntityMapper,
    private val serverPath: IServerPath
) : BaseViewModel() {

    fun getTimeTables(): LiveData<Resource<List<TimeTable>>> {
        return getTimeTableTask.buildUseCase()
            .map { t: List<FilesEntity> ->
                t.forEach {
                    it.photo = serverPath.setCorrectPath(it.photo)
                }
                timeTableEntityMapper.entityToPresentationList(t)
            }
            .map { t: List<TimeTable> ->
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

    fun getTimeTable(identifier: String): LiveData<Resource<TimeTable>> {
        return getTimeTableTask.buildUseCase(identifier)
            .map { t: List<FilesEntity> ->
                timeTableEntityMapper.entityToPresentation(t[0])
            }
            .map { t: TimeTable ->
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