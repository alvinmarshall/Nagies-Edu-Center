package com.cheise_proj.presentation.viewmodel.files

import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import com.cheise_proj.domain.entity.files.FilesEntity
import com.cheise_proj.domain.usecase.files.GetBillTask
import com.cheise_proj.presentation.extensions.asBillPresentation
import com.cheise_proj.presentation.extensions.asBillPresentationList
import com.cheise_proj.presentation.model.files.Bill
import com.cheise_proj.presentation.model.vo.Resource
import com.cheise_proj.presentation.utils.IServerPath
import com.cheise_proj.presentation.viewmodel.BaseViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

class BillViewModel @Inject constructor(
    private val getBillTask: GetBillTask,
    private val serverPath: IServerPath
) : BaseViewModel() {

    fun getBills(): LiveData<Resource<List<Bill>>> {
        return getBillTask.buildUseCase()
            .map { t: List<FilesEntity> ->
                t.forEach {
                    it.path = it.photo
                    it.photo = serverPath.setCorrectPath(it.photo)
                }
                t.asBillPresentationList()
            }
            .map { t: List<Bill> ->
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

    fun getBill(identifier: String): LiveData<Resource<Bill>> {
        return getBillTask.buildUseCase(identifier)
            .map { t: List<FilesEntity> ->
               t[0].asBillPresentation()
            }
            .map { t: Bill ->
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