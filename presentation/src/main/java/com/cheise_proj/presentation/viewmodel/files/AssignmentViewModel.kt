package com.cheise_proj.presentation.viewmodel.files

import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import com.cheise_proj.domain.entity.files.FilesEntity
import com.cheise_proj.domain.usecase.files.GetAssignmentTask
import com.cheise_proj.presentation.mapper.files.AssignmentEntityMapper
import com.cheise_proj.presentation.model.files.Assignment
import com.cheise_proj.presentation.model.vo.Resource
import com.cheise_proj.presentation.utils.IServerPath
import com.cheise_proj.presentation.viewmodel.BaseViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

class AssignmentViewModel @Inject constructor(
    private val getAssignmentTask: GetAssignmentTask,
    private val assignmentEntityMapper: AssignmentEntityMapper,
    private val serverPath: IServerPath
) : BaseViewModel() {

    fun getAssignments(): LiveData<Resource<List<Assignment>>> {
        return getAssignmentTask.buildUseCase()
            .map { t: List<FilesEntity> ->
                t.forEach {
                    it.photo = serverPath.setCorrectPath(it.photo)
                }
                assignmentEntityMapper.entityToPresentationList(t)
            }
            .map { t: List<Assignment> ->
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

    fun getAssignment(identifier: String): LiveData<Resource<Assignment>> {
        return getAssignmentTask.buildUseCase(identifier)
            .map { t: List<FilesEntity> ->
                assignmentEntityMapper.entityToPresentation(t[0])
            }
            .map { t: Assignment ->
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