package com.cheise_proj.presentation.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import com.cheise_proj.domain.usecase.users.GetUserTask
import com.cheise_proj.presentation.mapper.user.UserEntityMapper
import com.cheise_proj.presentation.model.user.User
import com.cheise_proj.presentation.model.vo.Resource
import com.cheise_proj.presentation.viewmodel.BaseViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val getUserTask: GetUserTask,
    private val userEntityMapper: UserEntityMapper
) : BaseViewModel() {
    fun authenticateUser(
        username: String,
        password: String,
        role: String
    ): LiveData<Resource<User>> {
        return getUserTask.buildUseCase(getUserTask.UserParams(username, password, role))
            .map { userEntityMapper.entityToPresentation(it) }
            .map { Resource.onSuccess(it) }
            .startWith(Resource.onLoading())
            .onErrorResumeNext(
                Function {
                    Observable.just(Resource.onError(it.localizedMessage))
                }
            )
            .toFlowable(BackpressureStrategy.LATEST).toLiveData()
    }
}