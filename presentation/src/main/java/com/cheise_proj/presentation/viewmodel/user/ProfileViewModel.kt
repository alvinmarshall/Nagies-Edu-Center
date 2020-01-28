package com.cheise_proj.presentation.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import com.cheise_proj.domain.usecase.users.GetProfileTask
import com.cheise_proj.presentation.mapper.user.ProfileEntityMapper
import com.cheise_proj.presentation.model.user.Profile
import com.cheise_proj.presentation.model.vo.Resource
import com.cheise_proj.presentation.viewmodel.BaseViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getProfileTask: GetProfileTask,
    private val profileEntityMapper: ProfileEntityMapper
) : BaseViewModel() {
    fun getProfile(role: String, identifier: String): LiveData<Resource<Profile>> {
        return getProfileTask.buildUseCase(getProfileTask.ProfileParams(role, identifier))
            .map { profileEntityMapper.entityToPresentation(it) }
            .map { Resource.onSuccess(it) }
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