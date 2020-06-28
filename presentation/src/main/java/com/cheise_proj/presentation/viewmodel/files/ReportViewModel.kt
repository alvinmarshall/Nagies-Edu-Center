package com.cheise_proj.presentation.viewmodel.files

import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import com.cheise_proj.domain.entity.files.FilesEntity
import com.cheise_proj.domain.usecase.files.DeleteReportTask
import com.cheise_proj.domain.usecase.files.GetReportTask
import com.cheise_proj.presentation.extensions.asReportPresentation
import com.cheise_proj.presentation.extensions.asReportPresentationList
import com.cheise_proj.presentation.model.files.Report
import com.cheise_proj.presentation.model.vo.Resource
import com.cheise_proj.presentation.utils.IServerPath
import com.cheise_proj.presentation.viewmodel.BaseViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

class ReportViewModel @Inject constructor(
    private val getReportTask: GetReportTask,
    private val serverPath: IServerPath,
    private val deleteReportTask: DeleteReportTask
) : BaseViewModel() {

    fun deleteReport(identifier: String, url: String): LiveData<Resource<Boolean>> {
        return deleteReportTask.buildUseCase(deleteReportTask.Params(identifier, url))
            .map { t: Boolean ->
                Resource.onSuccess(t)

            }
            .startWith(Resource.onLoading())
            .onErrorResumeNext(Function { Observable.just(Resource.onError(it.message)) })
            .toFlowable(BackpressureStrategy.LATEST)
            .toLiveData()

    }

    fun getReports(): LiveData<Resource<List<Report>>> {
        return getReportTask.buildUseCase()
            .map { t: List<FilesEntity> ->
                t.forEach {
                    it.path = it.photo
                    it.photo = serverPath.setCorrectPath(it.photo)
                }
                t.asReportPresentationList()
            }
            .map { t: List<Report> ->
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

    fun getReport(identifier: String): LiveData<Resource<Report>> {
        return getReportTask.buildUseCase(identifier)
            .map { t: List<FilesEntity> ->
                t[0].asReportPresentation()
            }
            .map { t: Report ->
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