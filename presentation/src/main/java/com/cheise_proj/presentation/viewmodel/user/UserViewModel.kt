package com.cheise_proj.presentation.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import com.cheise_proj.domain.usecase.users.GetUserTask
import com.cheise_proj.domain.usecase.users.UpdatePasswordTask
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
    private val userEntityMapper: UserEntityMapper,
    private val updatePasswordTask: UpdatePasswordTask
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

    fun updateAccountPassword(
        identifier: String,
        oldPassword: String,
        newPassword: String
    ): LiveData<Resource<Boolean>> {
        return updatePasswordTask.buildUseCase(
            updatePasswordTask.Params(
                identifier,
                oldPassword,
                newPassword
            )
        )
            .map {
                Resource.onSuccess(it)
            }
            .startWith(Resource.onLoading())
            .onErrorResumeNext(Function { Observable.just(Resource.onError(it.localizedMessage)) })
            .toFlowable(BackpressureStrategy.LATEST).toLiveData()
    }
}