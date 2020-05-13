package com.cheise_proj.presentation.viewmodel.files

import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import com.cheise_proj.domain.entity.files.FilesEntity
import com.cheise_proj.domain.usecase.files.DeleteAssignmentTask
import com.cheise_proj.domain.usecase.files.GetAssignmentTask
import com.cheise_proj.presentation.extensions.asAssignmentPresentation
import com.cheise_proj.presentation.extensions.asAssignmentPresentationList
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
    private val serverPath: IServerPath,
    private val deleteAssignmentTask: DeleteAssignmentTask
) : BaseViewModel() {


    fun deleteAssignment(identifier: String, url: String): LiveData<Resource<Boolean>> {
        return deleteAssignmentTask.buildUseCase(deleteAssignmentTask.Params(identifier, url))
            .map { t: Boolean ->
                Resource.onSuccess(t)

            }
            .startWith(Resource.onLoading())
            .onErrorResumeNext(Function { Observable.just(Resource.onError(it.message)) })
            .toFlowable(BackpressureStrategy.LATEST)
            .toLiveData()

    }

    fun getAssignments(): LiveData<Resource<List<Assignment>>> {
        return getAssignmentTask.buildUseCase()
            .map { t: List<FilesEntity> ->
                t.forEach {
                    it.path = it.photo
                    it.photo = serverPath.setCorrectPath(it.photo)
                }
                t.asAssignmentPresentationList()
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
               t[0].asAssignmentPresentation()
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