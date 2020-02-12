package com.cheise_proj.presentation.viewmodel.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import com.cheise_proj.domain.usecase.people.GetPeopleTask
import com.cheise_proj.presentation.mapper.people.PeopleEntityMapper
import com.cheise_proj.presentation.model.people.People
import com.cheise_proj.presentation.model.vo.Resource
import com.cheise_proj.presentation.viewmodel.BaseViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

class PeopleViewModel @Inject constructor(
    private val getPeopleTask: GetPeopleTask,
    private val peopleEntityMapper: PeopleEntityMapper
) : BaseViewModel() {

    fun getPeopleList(type: String): LiveData<Resource<List<People>>> {
        return getPeopleTask.buildUseCase(getPeopleTask.Params(type))
            .map {
                peopleEntityMapper.entityToPresentationList(it)
            }
            .map { Resource.onSuccess(it) }
            .startWith(Resource.onLoading())
            .onErrorResumeNext(Function {
                Observable.just(Resource.onError(it.localizedMessage))
            })
            .toFlowable(BackpressureStrategy.LATEST)
            .toLiveData()
    }

    fun getPeople(type: String, identifier: String): LiveData<Resource<People>> {
        return getPeopleTask.buildUseCase(getPeopleTask.Params(type, identifier))
            .map {
                peopleEntityMapper.entityToPresentationList(it)[0]
            }
            .map { Resource.onSuccess(it) }
            .startWith(Resource.onLoading())
            .onErrorResumeNext(Function {
                Observable.just(Resource.onError(it.localizedMessage))
            })
            .toFlowable(BackpressureStrategy.LATEST)
            .toLiveData()
    }
}