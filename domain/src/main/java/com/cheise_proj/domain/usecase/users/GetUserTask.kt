package com.cheise_proj.domain.usecase.users

import com.cheise_proj.domain.entity.user.UserEntity
import com.cheise_proj.domain.repository.UserRepository
import com.cheise_proj.domain.rx.ObservableUseCase
import com.cheise_proj.domain.rx.qualifier.Background
import com.cheise_proj.domain.rx.qualifier.Foreground
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class GetUserTask @Inject constructor(
    private val userRepository: UserRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
) :
    ObservableUseCase<UserEntity, GetUserTask.UserParams>(
        backgroundScheduler,
        foregroundScheduler
    ) {
    inner class UserParams(
        val username: String,
        val password: String,
        val role:String
    )

    override fun generateSingle(input: UserParams?): Observable<UserEntity> {
        if (input == null) throw IllegalArgumentException("login credentials can't be empty")
        return with(input) {
            userRepository.authenticateUser(username, password,role)
        }
    }

}

