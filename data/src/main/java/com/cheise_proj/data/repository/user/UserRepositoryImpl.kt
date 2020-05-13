package com.cheise_proj.data.repository.user

import com.cheise_proj.data.extensions.asEntity
import com.cheise_proj.data.model.user.ProfileData
import com.cheise_proj.data.model.user.UserData
import com.cheise_proj.data.source.LocalSource
import com.cheise_proj.data.source.RemoteSource
import com.cheise_proj.domain.entity.user.ProfileEntity
import com.cheise_proj.domain.entity.user.UserEntity
import com.cheise_proj.domain.repository.UserRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource
) : UserRepository {
    override fun authenticateUser(
        username: String,
        password: String,
        role: String
    ): Observable<UserEntity> {

        val local = localSource.getUser(username, password)
            .map { t: UserData ->
                t.asEntity()
            }.onErrorResumeNext { Single.error(Throwable("user not found")) }

        return remoteSource.authenticateUser(role, username, password)
            .map { t: UserData ->
                t.password = password
                t.username = username
                localSource.saveUser(t)
                t.asEntity()

            }
            .concatWith(local)
    }

    override fun getStudentProfile(identifier: String): Observable<ProfileEntity> {
        val local = localSource.getProfile(identifier)
            .map { t: ProfileData ->
                t.asEntity()

            }
            .toObservable().onErrorResumeNext(Observable.error(Throwable("User not found")))

        return remoteSource.getProfile()
            .map { t: ProfileData ->
                localSource.saveProfile(t)
                t.asEntity()
            }.onErrorResumeNext(Observable.empty())
            .concatWith(local)
    }

    override fun getTeacherProfile(identifier: String): Observable<ProfileEntity> {
        val local = localSource.getProfile(identifier)
            .map { t: ProfileData ->
                t.asEntity()
            }
            .toObservable().onErrorResumeNext(Observable.error(Throwable("User not found")))

        return remoteSource.getProfile()
            .map { t: ProfileData ->
                localSource.saveProfile(t)
                t.asEntity()
            }.onErrorResumeNext(Observable.empty())
            .concatWith(local)
    }

    override fun changePassword(
        identifier: String,
        oldPassword: String,
        newPassword: String
    ): Observable<Boolean> {
        return remoteSource.changePassword(oldPassword, newPassword)
            .map { t: Boolean ->
                if (t) {
                    localSource.updatePassword(identifier, newPassword)
                    return@map t
                }
                return@map false
            }
    }


}