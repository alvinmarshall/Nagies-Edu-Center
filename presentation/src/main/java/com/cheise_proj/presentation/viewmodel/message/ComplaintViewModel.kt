package com.cheise_proj.presentation.viewmodel.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import com.cheise_proj.domain.entity.message.ComplaintEntity
import com.cheise_proj.domain.usecase.message.GetComplaintTask
import com.cheise_proj.domain.usecase.message.GetSentComplaintTask
import com.cheise_proj.domain.usecase.message.SendComplaintTask
import com.cheise_proj.presentation.mapper.message.ComplaintEntityMapper
import com.cheise_proj.presentation.model.message.Complaint
import com.cheise_proj.presentation.model.vo.Resource
import com.cheise_proj.presentation.viewmodel.BaseViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

class ComplaintViewModel @Inject constructor(
    private val getComplaintTask: GetComplaintTask,
    private val complaintEntityMapper: ComplaintEntityMapper,
    private val getSentComplaintTask: GetSentComplaintTask,
    private val sendComplaintTask: SendComplaintTask

) : BaseViewModel() {

    fun sendComplaint(content: String, identifier: String): LiveData<Resource<Boolean>> {
        return sendComplaintTask.buildUseCase(sendComplaintTask.Params(content, identifier))
            .map { t: Boolean ->
                Resource.onSuccess(t)
            }
            .startWith(Resource.onLoading())
            .onErrorResumeNext(Function {
                Observable.just(Resource.onError(it.localizedMessage))
            })
            .toFlowable(BackpressureStrategy.LATEST)
            .toLiveData()
    }

    fun getSentComplaint(): LiveData<Resource<List<Complaint>>> {
        return getSentComplaintTask.buildUseCase()
            .map { t: List<ComplaintEntity> -> complaintEntityMapper.entityToPresentationList(t) }
            .map { t: List<Complaint> -> Resource.onSuccess(t) }
            .startWith(Resource.onLoading())
            .onErrorResumeNext(
                Function {
                    Observable.just(Resource.onError(it.localizedMessage))
                }
            )
            .toFlowable(BackpressureStrategy.LATEST)
            .toLiveData()
    }

    fun getComplaintList(): LiveData<Resource<List<Complaint>>> {
        return getComplaintTask.buildUseCase()
            .map { t: List<ComplaintEntity> ->
                complaintEntityMapper.entityToPresentationList(t)
            }
            .map { t: List<Complaint> ->
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

    fun getComplaint(identifier: String): LiveData<Resource<Complaint>> {
        return getComplaintTask.buildUseCase(identifier)
            .map { t: List<ComplaintEntity> ->
                complaintEntityMapper.entityToPresentationList(t).firstOrNull()
            }
            .map { t: Complaint ->
                Resource.onSuccess(t)
            }
            .startWith(Resource.onLoading())
            .onErrorResumeNext(Function {
                Observable.just(Resource.onError(it.localizedMessage))
            })
            .toFlowable(BackpressureStrategy.LATEST)
            .toLiveData()
    }
}