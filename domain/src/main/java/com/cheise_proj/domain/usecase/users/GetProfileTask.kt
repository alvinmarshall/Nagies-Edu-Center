package com.cheise_proj.domain.usecase.users

import com.cheise_proj.domain.entity.user.ProfileEntity
import com.cheise_proj.domain.repository.UserRepository
import com.cheise_proj.domain.rx.ObservableUseCase
import com.cheise_proj.domain.rx.qualifier.Background
import com.cheise_proj.domain.rx.qualifier.Foreground
import io.reactivex.Observable
import io.reactivex.Scheduler
import java.util.*
import javax.inject.Inject

class GetProfileTask @Inject constructor(
    private val userRepository: UserRepository,
    @Background backgroundScheduler: Scheduler,
    @Foreground foregroundScheduler: Scheduler
) :
    ObservableUseCase<ProfileEntity, GetProfileTask.ProfileParams>(backgroundScheduler, foregroundScheduler) {
    companion object {
        const val PARENT = "parent"
        const val TEACHER = "teacher"
    }

    inner class ProfileParams(
        val role: String,
        val identifier: String
    )

    override fun generateSingle(input: ProfileParams?): Observable<ProfileEntity> {
        if (input == null) throw IllegalArgumentException("profile params can't be empty")
        return when (input.role.toLowerCase(Locale.ENGLISH)) {
            PARENT -> userRepository.getStudentProfile(input.identifier)
            TEACHER -> userRepository.getTeacherProfile(input.identifier)
            else -> throw IllegalArgumentException("Unknown role $input")
        }
    }


}