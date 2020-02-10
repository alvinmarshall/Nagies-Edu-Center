package com.cheise_proj.domain.usecase.users

import com.cheise_proj.domain.repository.UserRepository
import com.cheise_proj.domain.rx.ObservableUseCase
import com.cheise_proj.domain.rx.qualifier.Background
import com.cheise_proj.domain.rx.qualifier.Foreground
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class UpdatePasswordTask @Inject constructor(
    private val userRepository: UserRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
) :
    ObservableUseCase<Boolean, UpdatePasswordTask.Params>(backgroundScheduler, foregroundScheduler) {
    inner class Params(
        val identifier:String,
        val oldPassword: String,
        val newPassword: String
    )

    override fun generateSingle(input: Params?): Observable<Boolean> {
        if (input == null) throw IllegalArgumentException("password params can't be empty")
        with(input) {
            return userRepository.changePassword(identifier,oldPassword, newPassword)
        }
    }


}